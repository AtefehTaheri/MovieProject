package ir.atefehtaheri.movieapp.data.topratedmovie.remote.api

import ir.atefehtaheri.network.BuildConfig
import ir.atefehtaheri.network.ErrorResponse
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.topratedmovie.remote.models.TopRatedMovieDto
import retrofit2.http.GET
import retrofit2.http.Query


interface TopRatedMovieApi {


    @GET("3/movie/top_rated?")
    suspend fun getTopRatedMovieList(
        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<TopRatedMovieDto, ErrorResponse>

}