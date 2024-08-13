package ir.atefehtaheri.movieapp.data.tvshowlist.remote.models

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.Movie


data class TvShow(
    val adult: Boolean,
    val backdrop_path: String?,
    val first_air_date: String,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val vote_average: Double,
    val vote_count: Int
)


fun TvShow.asMovieEntity(mediaType: MediaType.TvShow): MovieEntity {
    return MovieEntity(
        backdrop_path =backdrop_path,
        id =id,
        title =name,
        overview =overview,
        poster_path =poster_path,
        release_date =first_air_date,
        vote_average =vote_average,
        type_movie = mediaType.mediaType
    )
}
fun TvShow.asMovieEntity(mediaType: Type): MovieEntity {
    return MovieEntity(
        backdrop_path =backdrop_path,
        id =id,
        title =name,
        overview =overview,
        poster_path =poster_path,
        release_date =first_air_date,
        vote_average =vote_average,
        type_movie = mediaType.name
    )
}