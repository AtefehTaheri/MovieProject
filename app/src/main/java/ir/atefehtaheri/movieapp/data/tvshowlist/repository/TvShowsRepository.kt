package ir.atefehtaheri.movieapp.data.tvshowlist.repository

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel

interface TvShowsRepository {

    suspend fun getFirstPageTvShowPager(mediaType: MediaType.TvShow): ResultStatus<List<MovieDataModel>>

}