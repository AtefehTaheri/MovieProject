package ir.atefehtaheri.topratedmovie.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.topratedmovie.remote.models.TopRatedMovieDto

interface TopRatedMovieDatasource {

    suspend fun getFirstPageTopRatedMovie(): ResultStatus<TopRatedMovieDto>

}