package ir.atefehtaheri.movieapp.feature.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.repository.MoviesRepository
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.TvShowsRepository
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.HomeUiState
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.PagerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowRepository: TvShowsRepository,
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadContent()
    }

    private fun loadContent() {
        val movieMediaTypes = listOf(
            MediaType.Movie.UPCOMING,
            MediaType.Movie.TOP_RATED,
            MediaType.Movie.NOW_PLAYING
        )
        movieMediaTypes.forEach(::getDataMovie)

        val tvShowMediaTypes = listOf(
            MediaType.TvShow.Airing,
            MediaType.TvShow.TOP_RATED
        )
        tvShowMediaTypes.forEach(::getDataTvShow)
    }

    private fun getDataMovie(mediaType: MediaType.Movie) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    movies = it.movies + (mediaType to PagerState()),
                )
            }
            val response = moviesRepository.getFirstPageMoviesPager(mediaType)
            when (response) {
                is ResultStatus.Failure ->
                    _uiState.update {
                        it.copy(
                            movies = it.movies + (mediaType to PagerState(
                                null,
                                false,
                                response.exception_message
                            ))
                        )
                    }
                is ResultStatus.Success -> {
                    _uiState.update {
                        it.copy(
                            movies = it.movies + (mediaType to PagerState(
                                response.data,
                                false,
                                null
                            ))
                        )
                    }
                }
            }
        }
    }

    private fun getDataTvShow(mediaType: MediaType.TvShow) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    tvShows = it.tvShows + (mediaType to PagerState()),
                )
            }
            val response = tvShowRepository.getFirstPageTvShowPager(mediaType)
            when (response) {
                is ResultStatus.Failure ->
                    _uiState.update {
                        it.copy(
                            tvShows = it.tvShows + (mediaType to PagerState(
                                null,
                                false,
                                response.exception_message
                            ))
                        )
                    }
                is ResultStatus.Success -> {
                    _uiState.update {
                        it.copy(
                            tvShows = it.tvShows + (mediaType to PagerState(
                                response.data,
                                false,
                                null
                            ))
                        )
                    }
                }
            }
        }
    }
}

