package ir.atefehtaheri.movieapp.feature.favoritescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.data.favoritelist.local.FavoriteListDatasource
import ir.atefehtaheri.movieapp.feature.favoritescreen.uistate.FavoriteUiState
import ir.atefehtaheri.movieapp.feature.favoritescreen.uistate.FilterType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val favoriteListDatasource: FavoriteListDatasource
) : ViewModel() {


    private val _filterTypeState = MutableStateFlow(FilterType.All)
    private val _favoriteListState = _filterTypeState
        .flatMapLatest { filterTypeState ->
            when (filterTypeState) {

                FilterType.TvShow -> favoriteListDatasource.getFavoriteMoviesList(listOf(Type.TvShow.name))
                FilterType.Movie -> favoriteListDatasource.getFavoriteMoviesList(listOf(Type.Movie.name))
                FilterType.All -> favoriteListDatasource.getFavoriteMoviesList(
                    listOf(
                        Type.Movie.name,
                        Type.TvShow.name
                    )
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResultStatus.Success(emptyList())
        )


    private val _uiState = MutableStateFlow(FavoriteUiState())

    val uiState = combine(
        _uiState,
        _filterTypeState,
        _favoriteListState
    ) { uiState, filterType, favoriteList ->

        when (favoriteList) {
            is ResultStatus.Failure -> uiState.copy(
                isLoading = false,
                errorMessage = favoriteList.exception_message
            )

            is ResultStatus.Success -> uiState.copy(
                favoriteMovie = favoriteList.data ?: emptyList(),
                filterType = filterType,
                errorMessage = null,
                isLoading = false
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FavoriteUiState(isLoading = true)
    )

    fun updateFilterState(filterType: FilterType) {
        _filterTypeState.update {
            filterType
        }
    }
}