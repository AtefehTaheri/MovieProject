package ir.atefehtaheri.movieapp.data.tvshowlist.repository

import androidx.paging.PagingData
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
    suspend fun getFirstPageTvShowPager(mediaType: MediaType.TvShow): ResultStatus<List<MovieDataModel>>
    fun getTvShowPaging(type: MediaType.TvShow): Flow<PagingData<MovieDataModel>>

}