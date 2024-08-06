package ir.atefehtaheri.upcominglist.repository


import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.upcominglist.repository.models.UpcomingListDataModel

interface UpcomingListRepository {

    suspend fun getFirstPageUpcomingList(): ResultStatus<UpcomingListDataModel>


}