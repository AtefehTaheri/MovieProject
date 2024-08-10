package ir.atefehtaheri.movieapp.data.movieslist.repository.models

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.Movie
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto

//data class MovieListDataModel(
//    val movieDataModel: List<MovieDataModel>? = null,
//)

data class MovieDataModel(
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val type:String
)


//fun MoviesDto.asMovieListDataModel(): MovieListDataModel {
//    return MovieListDataModel(
//        movieDataModel = this.results.map {
//            it.asMovieDataModel()
//        }
//    )
//
//}

fun MoviesDto.asMovieListDataModel(): List<MovieDataModel> {
    return this.results.map {
            it.asMovieDataModel()
        }
}



fun Movie.asMovieDataModel(): MovieDataModel {
    return MovieDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        type= MediaType.Movie.name
    )
}
