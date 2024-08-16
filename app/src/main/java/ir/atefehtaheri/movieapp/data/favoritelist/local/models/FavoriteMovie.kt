package ir.atefehtaheri.movieapp.data.favoritelist.local.models

import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.entities.FavoriteMovieEntity


data class FavoriteMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val type_movie: Type
)


fun FavoriteMovie.asFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        type_movie = type_movie.name
    )
}

fun FavoriteMovieEntity.asFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        type_movie = when {
            type_movie.equals(Type.Movie.name) -> Type.Movie
            else -> Type.TvShow
        }
    )
}