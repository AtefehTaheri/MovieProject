package ir.atefehtaheri.movieapp.data.favoritelist.local

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.MovieDatabase
import ir.atefehtaheri.movieapp.core.database.dao.FavoriteMovieDao
import ir.atefehtaheri.movieapp.core.database.entities.FavoriteMovieEntity
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.FavoriteMovie
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.asFavoriteMovie
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.asFavoriteMovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class LocalFavoriteListDataSource @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val dispatcher: CoroutineDispatcher,
) : FavoriteListDatasource {


    override suspend fun addFavoriteMovie(favoriteMovie: FavoriteMovie): ResultStatus<Unit> {
        return withContext(dispatcher) {
            ensureActive()
            try {
                favoriteMovieDao.insertFavoriteMovieEntity(favoriteMovie.asFavoriteMovieEntity())
                ResultStatus.Success(Unit)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                } else {
                    ResultStatus.Failure(e.message ?: "Error")
                }
            }
        }
    }

    override suspend fun removeFavoriteMovie(mediaType: Type, id: Int): ResultStatus<Unit> {
        return withContext(dispatcher) {
            ensureActive()
            try {
                favoriteMovieDao.clearFavoriteMovieEntity(mediaType.name, id)
                ResultStatus.Success(Unit)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                } else {
                    ResultStatus.Failure(e.message ?: "Error")
                }
            }
        }
    }

    override suspend fun isFavoriteMovie(id: Int): ResultStatus<Boolean> {
        return withContext(dispatcher) {
            ensureActive()
            try {
                ResultStatus.Success(favoriteMovieDao.isFavorite(id))
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                } else {
                    ResultStatus.Failure(e.message ?: "Error")
                }
            }
        }
    }

    override suspend fun getFavoriteMoviesList(type_movie: List<String>): Flow<ResultStatus<List<FavoriteMovie>>> {

            return favoriteMovieDao.getFavoriteMovieEntityByType(type_movie)
                .map<List<FavoriteMovieEntity>, ResultStatus<List<FavoriteMovie>>> {
                    ResultStatus.Success(
                        it.map { favoriteMovieEntity ->
                            favoriteMovieEntity.asFavoriteMovie()
                        }
                    )
                }.catch {
                    emit(ResultStatus.Failure(it.message ?: "Error"))
                }.flowOn(dispatcher)
    }
}