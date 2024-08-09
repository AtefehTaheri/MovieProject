package ir.atefehtaheri.movieapp.data.movieslist.remote

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto

interface MoviesDatasource {

    suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<MoviesDto>

}