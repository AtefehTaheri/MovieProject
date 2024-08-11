package ir.atefehtaheri.movieapp.data.movieslist.remote.models

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel


data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun Movie.asMovieEntity(mediaType: MediaType.Movie): MovieEntity {
    return MovieEntity(
        backdrop_path =backdrop_path,
        id =id,
        title =title,
        overview =overview,
        poster_path =poster_path,
        release_date =release_date,
        vote_average =vote_average,
        type_movie = mediaType.mediaType
    )
}

