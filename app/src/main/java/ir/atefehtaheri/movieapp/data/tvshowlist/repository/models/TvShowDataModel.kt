package ir.atefehtaheri.movieapp.data.tvshowlist.repository.models

import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShow
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShowsDto


fun TvShowsDto.asMovieListDataModel(): List<MovieDataModel> {
    return this.results.map {
        it.asMovieDataModel()
    }
}


fun TvShow.asMovieDataModel(): MovieDataModel {
    return MovieDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = name,
        overview = overview,
        poster_path = poster_path,
        release_date = first_air_date,
        vote_average = vote_average
    )
}






