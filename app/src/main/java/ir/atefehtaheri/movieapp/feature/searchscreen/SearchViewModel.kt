package ir.atefehtaheri.movieapp.feature.searchscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.data.movieslist.repository.MoviesRepository
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.TvShowsRepository
import ir.atefehtaheri.movieapp.feature.searchscreen.uistate.SearchUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()


    private var job: Job? = null

    fun updateQuery(query: String) {
        val isSearching = !query.equals("")
        _searchQuery.value = query
        _uiState.update {
            it.copy(isSearching = isSearching)
        }
        if (isSearching) {

            job?.cancel()
            job = viewModelScope.launch {
                delay(1000)

                val searchMovieResult = moviesRepository.getSearchMoviesPaging(query)
                    .cachedIn(viewModelScope)
                _uiState.update {
                    it.copy(movies = searchMovieResult)
                }
                val searchTvShowResult = tvShowsRepository.getSearchTvShowPaging(query)
                    .cachedIn(viewModelScope)
                _uiState.update {
                    it.copy(tvShows = searchTvShowResult)
                }
            }
        } else {
            _uiState.update {
                it.copy(movies = emptyFlow(), tvShows = emptyFlow())
            }
        }
    }




}