package ir.atefehtaheri.movieapp.data.movieslist.remote.api

import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto
import ir.atefehtaheri.network.ErrorResponse
import ir.atefehtaheri.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {



    @GET("3/movie/now_playing?")
    suspend fun getNowPlaying(
//        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<MoviesDto, ErrorResponse>


    @GET("3/movie/top_rated?")
    suspend fun getTopRatedMovieList(
//        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<MoviesDto, ErrorResponse>


    @GET("3/movie/upcoming?")
    suspend fun getUpcomingList(
//        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<MoviesDto, ErrorResponse>

}