package ir.atefehtaheri.movieapp.feature.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.data.detailitem.repository.DetailItemRepository
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.asFavoriteMovie
import ir.atefehtaheri.movieapp.data.favoritelist.local.FavoriteListDatasource
import ir.atefehtaheri.movieapp.feature.detailscreen.uistate.DetailState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailItemRepository: DetailItemRepository,
    private val favoriteListDatasource: FavoriteListDatasource
) : ViewModel() {


    private val _detailMovie = MutableStateFlow(DetailState())
    val detailMovie = _detailMovie.asStateFlow()


    fun getDetailMovie(movieid: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _detailMovie.update {
                    it.copy(
                        null,
                        null,
                        true,
                        null
                    )
                }

                val response = detailItemRepository.getDetailMovie(movieid)
                when (response) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = response.exception_message)
                        }

                    is ResultStatus.Success -> {
                        _detailMovie.update {
                            it.copy(
                                response.data,
                                null,
                                false,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    fun getDetailTvShow(tvshowid: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _detailMovie.update {
                    it.copy(
                        null,
                        null,
                        true,
                        null
                    )
                }
                val response = detailItemRepository.getDetailTvShow(tvshowid)
                when (response) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = response.exception_message)
                        }

                    is ResultStatus.Success -> {
                        _detailMovie.update {
                            it.copy(
                                null,
                                response.data,
                                false,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    fun addToFavoriteList(mediaType: Type) {
        when (mediaType) {
            Type.Movie -> addMovieToFavoriteList()
            Type.TvShow -> addTvShowToFavoriteList()
        }

    }

    fun removeFavoriteList(mediaType: Type, id: Int) {
        when (mediaType) {
            Type.Movie -> removeMovieFavoriteList(id)
            Type.TvShow -> removeTvShowFavoriteList(id)
        }

    }

    fun addMovieToFavoriteList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = favoriteListDatasource.addFavoriteMovie(
                    _detailMovie.value.MovieDetailDataModel!!.asFavoriteMovie()
                )
                when (result) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = result.exception_message)
                        }

                    is ResultStatus.Success -> {
                        val favoriteMovie =
                            _detailMovie.value.MovieDetailDataModel!!.copy(isFavorite = true)
                        _detailMovie.update {
                            it.copy(MovieDetailDataModel = favoriteMovie)
                        }
                    }
                }
            }
        }
    }

    fun removeMovieFavoriteList(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = favoriteListDatasource.removeFavoriteMovie(
                    Type.Movie,
                    id
                )
                when (result) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = result.exception_message)
                        }

                    is ResultStatus.Success -> {
                        val favoriteMovie =
                            _detailMovie.value.MovieDetailDataModel!!.copy(isFavorite = false)
                        _detailMovie.update {
                            it.copy(MovieDetailDataModel = favoriteMovie)
                        }

                    }
                }
            }
        }
    }


    fun addTvShowToFavoriteList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = favoriteListDatasource.addFavoriteMovie(
                    _detailMovie.value.tvShowDetailDataModel!!.asFavoriteMovie()
                )
                when (result) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = result.exception_message)
                        }

                    is ResultStatus.Success -> {
                        val favoriteTvShow =
                            _detailMovie.value.tvShowDetailDataModel!!.copy(isFavorite = true)
                        _detailMovie.update {
                            it.copy(tvShowDetailDataModel = favoriteTvShow)
                        }
                    }
                }
            }

        }
    }


    fun removeTvShowFavoriteList(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = favoriteListDatasource.removeFavoriteMovie(
                    Type.TvShow,
                    id
                )
                when (result) {
                    is ResultStatus.Failure ->
                        _detailMovie.update {
                            it.copy(errorMessage = result.exception_message)
                        }

                    is ResultStatus.Success -> {
                        val favoriteTvShow =
                            _detailMovie.value.tvShowDetailDataModel!!.copy(isFavorite = false)
                        _detailMovie.update {
                            it.copy(tvShowDetailDataModel = favoriteTvShow)
                        }

                    }
                }
            }
        }
    }
}

