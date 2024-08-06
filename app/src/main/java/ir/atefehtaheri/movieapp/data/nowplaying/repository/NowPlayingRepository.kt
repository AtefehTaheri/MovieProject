package ir.atefehtaheri.nowplaying.repository

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.nowplaying.repository.models.NowPlayingListDataModel

interface NowPlayingRepository {

    suspend fun getFirstPageNowPlaying(): ResultStatus<NowPlayingListDataModel>

}