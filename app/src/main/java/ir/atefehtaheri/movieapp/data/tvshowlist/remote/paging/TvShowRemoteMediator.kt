package ir.atefehtaheri.movieapp.data.tvshowlist.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.database.MovieDatabase
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity
import ir.atefehtaheri.movieapp.core.database.entities.RemoteKey
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.TvShowsDatasource
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.asMovieEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator @Inject constructor(
    private val tvShowsDatasource: TvShowsDatasource,
    private val movieDatabase: MovieDatabase,
    private val type: MediaType.TvShow
) : RemoteMediator<Int, MovieEntity>() {

    private val remoteKeyDao = movieDatabase.remoteKeyDao
    private val movieDao = movieDatabase.movieDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {


        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = movieDatabase.withTransaction {
                        remoteKeyDao.getKeyByMovie(type.mediaType)
                    } ?: return MediatorResult.Success(true)

                    remoteKey.next_page ?: return MediatorResult.Success(true)
                }
            }

            val networkResponse = tvShowsDatasource.getTvShowPager(type, page)

            when (networkResponse) {
                is ResultStatus.Failure -> MediatorResult.Error(Throwable(networkResponse.exception_message))
                is ResultStatus.Success -> {

                    movieDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.clearMovieEntityByType(type.mediaType)
                        }
                        val data =
                            networkResponse.data?.results?.map { it.asMovieEntity(type) }
                                ?: emptyList()

                        val nextPage = if (networkResponse.data?.results!!.isEmpty()) {
                            null
                        } else {
                            page + 1
                        }
                        remoteKeyDao.insertKey(
                            RemoteKey(
                                id = type.mediaType,
                                next_page = nextPage,
                                prev_page = 0
                            )
                        )
                        movieDao.insertAllMovieEntity(data)
                    }
                    MediatorResult.Success(
                        endOfPaginationReached = networkResponse.data?.results!!.isEmpty()
                    )
                }
            }

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}




