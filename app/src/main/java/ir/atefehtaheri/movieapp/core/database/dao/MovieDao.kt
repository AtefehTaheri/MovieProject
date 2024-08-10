package ir.atefehtaheri.movieapp.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insertUpcomingAll(movies:List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE type_movie = :mediaType")
    fun pagingSourceMovieEntity(mediaType: String):PagingSource<Int, MovieEntity>

    @Query("DELETE FROM MovieEntity")
    fun clearAllMovieEntity()

    @Query("DELETE FROM MovieEntity WHERE type_movie = :mediaType")
    fun clearMovieEntityByType(mediaType: String)
}
