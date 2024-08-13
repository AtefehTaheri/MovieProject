package ir.atefehtaheri.movieapp.data.movieslist.remote

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto

interface MoviesDatasource {

    suspend fun getMoviesPager(mediaType: MediaType.Movie,page: Int): ResultStatus<MoviesDto>
    suspend fun getSearchMoviesPager(query: String,page: Int): ResultStatus<MoviesDto>
}