package ir.atefehtaheri.topratedmovie.repository

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.topratedmovie.repository.models.TopRatedMovieListDataModel


interface TopRatedMovieRepository {

    suspend fun getFirstPageTopRatedMovie(): ResultStatus<TopRatedMovieListDataModel>


}