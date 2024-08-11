package ir.atefehtaheri.movieapp.data.tvshowlist.remote

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShowsDto

interface TvShowsDatasource {
    suspend fun getTvShowPager(mediaType: MediaType.TvShow,page: Int): ResultStatus<TvShowsDto>
}