package ir.atefehtaheri.movieapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.atefehtaheri.movieapp.core.database.entities.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Insert
    fun insertFavoriteMovieEntity(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM FavoriteMovieEntity WHERE type_movie IN (:type_movie)")
    fun getFavoriteMovieEntityByType(type_movie: List<String>): Flow<List<FavoriteMovieEntity>>

    @Query("DELETE FROM FavoriteMovieEntity WHERE type_movie = :mediaType AND id = :id")
    fun clearFavoriteMovieEntity(mediaType: String, id: Int)

    @Query("SELECT COUNT(*) FROM FavoriteMovieEntity WHERE id = :id")
    fun countItemById(id: Int): Int

    fun isFavorite(id: Int): Boolean {
        return countItemById(id) > 0
    }
}
