package ir.atefehtaheri.movieapp.feature.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.data.movieslist.repository.MoviesRepository
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.TvShowsRepository
import ir.atefehtaheri.movieapp.feature.listscreen.uistate.ListUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState = _uiState.asStateFlow()


    fun getDataMovie(mediaType: MediaType.Movie) {
        val movies: Flow<PagingData<MovieDataModel>> = moviesRepository.getMoviesPaging(mediaType)
            .cachedIn(viewModelScope)

        _uiState.update {
            it.copy(movies = movies)
        }
    }

    fun getDataTvShow(mediaType: MediaType.TvShow) {
        val tvShows: Flow<PagingData<MovieDataModel>> = tvShowsRepository.getTvShowPaging(mediaType)
            .cachedIn(viewModelScope)
        _uiState.update {
            it.copy(tvShows = tvShows)
        }
    }
}
