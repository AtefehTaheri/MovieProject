package ir.atefehtaheri.movieapp.data.movieslist.repository

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel

interface MoviesRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }


    suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<List<MovieDataModel>>
//    fun getgMoviesPaging(type: MediaType.Movie): Flow<PagingData<MovieDataModel>>

}