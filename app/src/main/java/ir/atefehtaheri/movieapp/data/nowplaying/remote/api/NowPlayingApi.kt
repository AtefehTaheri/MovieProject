package ir.atefehtaheri.movieapp.data.nowplaying.remote.api

import ir.atefehtaheri.network.ErrorResponse
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.nowplaying.remote.models.NowPlayingDto
import retrofit2.http.GET
import retrofit2.http.Query


interface NowPlayingApi {


    @GET("3/movie/now_playing?")
    suspend fun getNowPlaying(
        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<NowPlayingDto, ErrorResponse>

}