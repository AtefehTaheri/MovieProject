package ir.atefehtaheri.nowplaying.repository

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.nowplaying.repository.models.NowPlayingListDataModel
import ir.atefehtaheri.movieapp.data.nowplaying.repository.models.asNowPlayingListDataModel
import ir.atefehtaheri.nowplaying.remote.NowPlayingDatasource
import javax.inject.Inject

class OnlineNowPlayingRepository @Inject constructor(
    private val nowPlayingDatasource: NowPlayingDatasource,
    ) : NowPlayingRepository {

    override suspend fun getFirstPageNowPlaying(): ResultStatus<NowPlayingListDataModel> {
        return when (val result = nowPlayingDatasource.getFirstPageNowPlaying()) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asNowPlayingListDataModel())
        }
    }

}