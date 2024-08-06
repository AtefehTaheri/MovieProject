package ir.atefehtaheri.movieapp.data.topratedmovie.repository.models

import ir.atefehtaheri.topratedmovie.remote.models.Movie
import ir.atefehtaheri.topratedmovie.remote.models.TopRatedMovieDto


data class TopRatedMovieListDataModel(
    val topRatedMovieList: List<TopRatedMovieDataModel>? = null,
)

data class TopRatedMovieDataModel(
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double
)


fun TopRatedMovieDto.asTopRatedMovieListDataModel(): TopRatedMovieListDataModel {
    return TopRatedMovieListDataModel(
        topRatedMovieList = this.results.map {
            it.asTopRatedMovieDataModel()
        }
    )

}

fun Movie.asTopRatedMovieDataModel(): TopRatedMovieDataModel {
    return TopRatedMovieDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average
    )
}

