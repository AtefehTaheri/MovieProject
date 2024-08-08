package ir.atefehtaheri.upcominglist.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.upcominglist.remote.api.UpcomingListApi
import ir.atefehtaheri.network.NetworkResponse
import ir.atefehtaheri.upcominglist.remote.models.UpcomingListDto
import javax.inject.Inject

class OnlineUpcomingListDatasource @Inject constructor(
    private val upcomingListApi : UpcomingListApi,
    ): UpcomingListDatasource {

    override suspend fun getFirstPageUpcomingList(): ResultStatus<UpcomingListDto> {

       return when(val result =upcomingListApi.getUpcomingList()){
           is NetworkResponse.ApiError -> ResultStatus.Failure(result.body.status_message)
           is NetworkResponse.NetworkError -> ResultStatus.Failure(result.error.message ?: "NetworkError")
           is NetworkResponse.Success -> ResultStatus.Success(result.body)
           is NetworkResponse.UnknownError -> ResultStatus.Failure(result.error.message ?: "UnknownError")
       }
    }

}