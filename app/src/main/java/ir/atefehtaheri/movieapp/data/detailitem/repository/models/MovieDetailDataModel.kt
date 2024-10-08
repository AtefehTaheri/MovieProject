package ir.atefehtaheri.movieapp.data.detailitem.repository.models

import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.movie.MovieDetailDto
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.FavoriteMovie


data class MovieDetailDataModel(
    val adult: Boolean,
    val credits: Credits,
    val genres: List<String>,
    val id: Int,
    val images: List<String>,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
    val isFavorite: Boolean
)

fun MovieDetailDto.asMovieDetailDataModel(isFavorite:Boolean): MovieDetailDataModel {

    return MovieDetailDataModel(
        genres = genres.map { it.name },
        adult = adult,
        poster_path = poster_path,
        title = title,
        id = id,
        runtime = runtime,
        vote_average = vote_average,
        status = status,
        release_date = release_date,
        overview = overview,
        credits = Credits(
            credits.cast.map { Cast(it.name, it.profile_path) }, credits.crew.map {
                Crew(it.job, it.name, it.profile_path)
            }),
        images = images.backdrops.map { it.file_path },
        isFavorite =isFavorite
    )
}

fun MovieDetailDataModel.asFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        type_movie = Type.Movie,
    )

}