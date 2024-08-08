package ir.atefehtaheri.topratedmovie.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.topratedmovie.remote.api.TopRatedMovieApi
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.topratedmovie.remote.models.TopRatedMovieDto
import javax.inject.Inject

class OnlineTopRatedMovieDatasource @Inject constructor(
    private val topRatedMovieApi : TopRatedMovieApi,

    ): TopRatedMovieDatasource {
    override suspend fun getFirstPageTopRatedMovie(): ResultStatus<TopRatedMovieDto> {
       return when(val result =topRatedMovieApi.getTopRatedMovieList()){
           is NetworkResponse.ApiError -> ResultStatus.Failure(result.body.status_message)
           is NetworkResponse.NetworkError -> ResultStatus.Failure(result.error.message ?: "NetworkError")
           is NetworkResponse.Success -> ResultStatus.Success(result.body)
           is NetworkResponse.UnknownError -> ResultStatus.Failure(result.error.message ?: "UnknownError")
       }
    }

}