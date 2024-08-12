package ir.atefehtaheri.movieapp.data.movieslist.remote.paging

import android.util.Log
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
import ir.atefehtaheri.movieapp.data.movieslist.remote.MoviesDatasource
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.asMovieEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val moviesDatasource: MoviesDatasource,
    private val movieDatabase: MovieDatabase,
    private val type: MediaType.Movie
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
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.next_page?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
//
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prev_page ?: return MediatorResult.Success(
                        remoteKey != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.next_page ?: return MediatorResult.Success(
                        remoteKey != null
                    )
                    nextPage
                }
            }

            val networkResponse = moviesDatasource.getMoviesPager(type, page)

            when (networkResponse) {
                is ResultStatus.Failure -> MediatorResult.Error(Throwable(networkResponse.exception_message))
                is ResultStatus.Success -> {

                    val data =
                        networkResponse.data?.results?.map { it.asMovieEntity(type) }
                            ?: emptyList()

                    val endOfPaginationReached = networkResponse.data?.results!!.isEmpty()

                    val prevPage = if (page == 1) null else page - 1
                    val nextPage = if (endOfPaginationReached) null else page + 1


                    movieDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.clearMovieEntityByType(type.mediaType)
                            remoteKeyDao.clearKeys(type.mediaType)
                        }

                        val movieKeys = movieDao.insertAllMovieEntity(data)
                        val remoteKeys = data.mapIndexed  { index,movieEtity ->

                            RemoteKey(
                                mediaType = type.mediaType,
                                next_page = nextPage,
                                prev_page = prevPage,
                                id = movieKeys.get(index).toInt()
                            )
                        }

                        remoteKeyDao.insertKeys(remoteKeys)
                    }
                    MediatorResult.Success(
                        endOfPaginationReached = endOfPaginationReached
                    )
                }
            }

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { entity ->
            Log.d("sss", "aaa" + entity.id_auto)

            remoteKeyDao.getKeyById(
                entity.id_auto
            )
        }

    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { entity ->
            remoteKeyDao.getKeyById(
                entity.id_auto
            )
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKey? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.let { entity ->
            remoteKeyDao.getKeyById(
                entity.id_auto

            )
        }

    }
}

