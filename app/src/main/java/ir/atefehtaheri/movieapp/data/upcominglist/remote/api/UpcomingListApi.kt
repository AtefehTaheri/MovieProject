package ir.atefehtaheri.movieapp.data.upcominglist.remote.api

import ir.atefehtaheri.network.ErrorResponse
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.upcominglist.remote.models.UpcomingListDto
import retrofit2.http.GET
import retrofit2.http.Query


interface UpcomingListApi {

    @GET("3/movie/upcoming?")
    suspend fun getUpcomingList(
        @Query("language") language:String= "en-US",
        @Query("page") page:Int = 1,
    ): NetworkResponse<UpcomingListDto, ErrorResponse>

}