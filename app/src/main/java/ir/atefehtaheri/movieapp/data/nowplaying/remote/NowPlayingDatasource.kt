package ir.atefehtaheri.nowplaying.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.nowplaying.remote.models.NowPlayingDto

interface NowPlayingDatasource {

    suspend fun getFirstPageNowPlaying(): ResultStatus<NowPlayingDto>

}