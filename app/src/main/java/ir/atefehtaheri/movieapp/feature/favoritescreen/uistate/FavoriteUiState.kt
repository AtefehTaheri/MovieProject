package ir.atefehtaheri.movieapp.feature.favoritescreen.uistate

import ir.atefehtaheri.movieapp.data.favoritelist.local.models.FavoriteMovie

data class FavoriteUiState(
    val isLoading: Boolean = true,
    val favoriteMovie: List<FavoriteMovie> = emptyList(),
    val errorMessage: String? = null,
    val filterType: FilterType = FilterType.All
)

enum class FilterType {
    TvShow,
    Movie,
    All
}