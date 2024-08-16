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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository
) : ViewModel() {

    private val _movietype = MutableStateFlow(MediaType.Movie.UPCOMING)
    private val _tvshowtype = MutableStateFlow(MediaType.TvShow.Airing)


    val movies = _movietype.flatMapLatest {
        moviesRepository.getMoviesPaging(it)
    }.cachedIn(viewModelScope)

    val tvShows = _tvshowtype.flatMapLatest {
        tvShowsRepository.getTvShowPaging(it)
    }.cachedIn(viewModelScope)



    fun updateType(typeMovie: MediaType.Movie, typeTvShow: MediaType.TvShow) {
        _movietype.value = typeMovie
        _tvshowtype.value = typeTvShow
    }

}
