package ir.atefehtaheri.movieapp.data.favoritelist.local

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.entities.FavoriteMovieEntity
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteListDatasource {

    suspend fun addFavoriteMovie(favoriteMovie: FavoriteMovie): ResultStatus<Unit>
    suspend fun removeFavoriteMovie(mediaType: Type, id: Int): ResultStatus<Unit>
    suspend fun isFavoriteMovie(id: Int): ResultStatus<Boolean>
    suspend fun getFavoriteMoviesList(type_movie:List<String>): Flow<ResultStatus<List<FavoriteMovie>>>
}