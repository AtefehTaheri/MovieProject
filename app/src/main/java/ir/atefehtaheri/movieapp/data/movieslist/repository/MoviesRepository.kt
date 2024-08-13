package ir.atefehtaheri.movieapp.data.movieslist.repository

import androidx.paging.PagingData
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }


    suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<List<MovieDataModel>>
    fun getMoviesPaging(type: MediaType.Movie): Flow<PagingData<MovieDataModel>>
    fun getSearchMoviesPaging(query:String): Flow<PagingData<MovieDataModel>>

}