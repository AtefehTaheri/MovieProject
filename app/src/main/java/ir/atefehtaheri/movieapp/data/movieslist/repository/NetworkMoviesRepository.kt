package ir.atefehtaheri.movieapp.data.movieslist.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.MovieDatabase
import ir.atefehtaheri.movieapp.data.movieslist.remote.MoviesDatasource
import ir.atefehtaheri.movieapp.data.movieslist.remote.paging.MovieRemoteMediator
import ir.atefehtaheri.movieapp.data.movieslist.remote.paging.SearchMovieRemoteMediator
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.asMovieDataModel
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.asMovieListDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkMoviesRepository @Inject constructor(
    private val moviesDatasource: MoviesDatasource,
    private val movieDatabase: MovieDatabase,
    private val dispatcher: CoroutineDispatcher,
) : MoviesRepository {

    override suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<List<MovieDataModel>> {
        return withContext(dispatcher) {
            ensureActive()
            when (val result = moviesDatasource.getMoviesPager(mediaType, 1)) {
                is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
                is ResultStatus.Success -> ResultStatus.Success(
                    result.data?.asMovieListDataModel(
                        mediaType
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesPaging(mediaType: MediaType.Movie): Flow<PagingData<MovieDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = MovieRemoteMediator(moviesDatasource, movieDatabase, mediaType),
            pagingSourceFactory = { movieDatabase.movieDao.pagingSourceMovieEntity(mediaType.mediaType) }
        ).flow.map {
            it.map {
                it.asMovieDataModel(mediaType)
            }
        }.flowOn(dispatcher)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchMoviesPaging(query: String): Flow<PagingData<MovieDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviesRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = SearchMovieRemoteMediator(moviesDatasource, movieDatabase, query),
            pagingSourceFactory = { movieDatabase.movieDao.pagingSourceMovieEntity(Type.Movie.name) }
        ).flow.map {
            it.map {
                it.asMovieDataModel(MediaType.Movie.TOP_RATED)
            }
        }.flowOn(dispatcher)
    }
}