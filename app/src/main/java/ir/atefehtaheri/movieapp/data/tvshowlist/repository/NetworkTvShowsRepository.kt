package ir.atefehtaheri.movieapp.data.tvshowlist.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.MovieDatabase
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.asMovieDataModel
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.TvShowsDatasource
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.paging.SearchTvShowRemoteMediator
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.paging.TvShowRemoteMediator
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.models.asMovieListDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetworkTvShowsRepository @Inject constructor(
    private val tvShowsDatasource: TvShowsDatasource,
    private val movieDatabase: MovieDatabase,
) : TvShowsRepository {

    override suspend fun getFirstPageTvShowPager(mediaType: MediaType.TvShow): ResultStatus<List<MovieDataModel>> {

        return when (val result = tvShowsDatasource.getTvShowPager(mediaType, 1)) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(
                result.data?.asMovieListDataModel(
                    mediaType
                )
            )
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getTvShowPaging(mediaType: MediaType.TvShow): Flow<PagingData<MovieDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = TvShowsRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = TvShowRemoteMediator(tvShowsDatasource, movieDatabase, mediaType),
            pagingSourceFactory = { movieDatabase.movieDao.pagingSourceMovieEntity(mediaType.mediaType) }
        ).flow.map {
            it.map {
                it.asMovieDataModel(mediaType)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchTvShowPaging(query: String): Flow<PagingData<MovieDataModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = TvShowsRepository.NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            remoteMediator = SearchTvShowRemoteMediator(tvShowsDatasource, movieDatabase, query),
            pagingSourceFactory = { movieDatabase.movieDao.pagingSourceMovieEntity(Type.TvShow.name) }
        ).flow.map {
            it.map {
                it.asMovieDataModel(MediaType.TvShow.TOP_RATED)
            }
        }
    }

}