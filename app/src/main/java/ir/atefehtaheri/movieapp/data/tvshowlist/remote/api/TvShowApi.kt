package ir.atefehtaheri.movieapp.data.tvshowlist.remote.api

import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShowsDto
import ir.atefehtaheri.network.ErrorResponse
import ir.atefehtaheri.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApi {

    @GET("3/tv/top_rated?")
    suspend fun getTopRatedTvShowList(
//        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<TvShowsDto, ErrorResponse>



    @GET("3/tv/airing_today?")
    suspend fun getTvAiringList(
//        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<TvShowsDto, ErrorResponse>

}