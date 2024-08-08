package ir.atefehtaheri.nowplaying.remote


import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.nowplaying.remote.api.NowPlayingApi
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.nowplaying.remote.models.NowPlayingDto
import javax.inject.Inject

class OnlineNowPlayingDatasource @Inject constructor(
    private val nowPlayingApi : NowPlayingApi,
    ): NowPlayingDatasource {
    override suspend fun getFirstPageNowPlaying(): ResultStatus<NowPlayingDto> {

       return when(val result =nowPlayingApi.getNowPlaying()){
           is NetworkResponse.ApiError -> ResultStatus.Failure(result.body.status_message)
           is NetworkResponse.NetworkError -> ResultStatus.Failure(result.error.message ?: "NetworkError")
           is NetworkResponse.Success -> ResultStatus.Success(result.body)
           is NetworkResponse.UnknownError -> ResultStatus.Failure(result.error.message ?: "UnknownError")
       }
    }

}