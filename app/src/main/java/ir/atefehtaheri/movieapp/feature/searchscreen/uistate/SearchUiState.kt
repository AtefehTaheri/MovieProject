package ir.atefehtaheri.movieapp.feature.searchscreen.uistate

import androidx.paging.PagingData
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val movies: Flow<PagingData<MovieDataModel>> = emptyFlow(),
    val tvShows: Flow<PagingData<MovieDataModel>> = emptyFlow()
)
