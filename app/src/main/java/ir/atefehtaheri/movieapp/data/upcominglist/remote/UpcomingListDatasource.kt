package ir.atefehtaheri.upcominglist.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.upcominglist.remote.models.UpcomingListDto

interface UpcomingListDatasource {

    suspend fun getFirstPageUpcomingList(): ResultStatus<UpcomingListDto>

}