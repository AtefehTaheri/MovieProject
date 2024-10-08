package ir.atefehtaheri.movieapp.data.movieslist.repository.models

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.database.entities.MovieEntity
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
    val media_type: MediaType
)


fun MoviesDto.asMovieListDataModel(media_type: MediaType.Movie): List<MovieDataModel> {
    return this.results.map {
        it.asMovieDataModel(media_type)
    }
}



fun Movie.asMovieDataModel(media_type: MediaType.Movie): MovieDataModel {
    return MovieDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        media_type = media_type
    )
}


fun MovieEntity.asMovieDataModel(media_type: MediaType): MovieDataModel {
    return MovieDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average,
        media_type = media_type
    )
}

