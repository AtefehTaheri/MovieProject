package ir.atefehtaheri.upcominglist.repository


import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.upcominglist.repository.models.UpcomingListDataModel
import ir.atefehtaheri.movieapp.data.upcominglist.repository.models.asUpcomingListDataModel
import ir.atefehtaheri.upcominglist.remote.UpcomingListDatasource
import javax.inject.Inject

class OnlineUpcomingListRepository @Inject constructor(
    private val upcomingListDatasource: UpcomingListDatasource,
    ) : UpcomingListRepository {

    override suspend fun getFirstPageUpcomingList(): ResultStatus<UpcomingListDataModel> {
        return when (val result = upcomingListDatasource.getFirstPageUpcomingList()) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asUpcomingListDataModel())
        }
    }


}