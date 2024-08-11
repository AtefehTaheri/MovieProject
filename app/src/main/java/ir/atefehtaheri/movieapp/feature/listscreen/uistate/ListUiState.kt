package ir.atefehtaheri.movieapp.feature.listscreen.uistate

import androidx.paging.PagingData
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class ListUiState(
    val movies: Flow<PagingData<MovieDataModel>> = flow{},
    val tvShows: Flow<PagingData<MovieDataModel>> =flow{}
)
