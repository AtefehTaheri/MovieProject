package ir.atefehtaheri.movieapp.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovieEntity")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val type_movie:String
)

