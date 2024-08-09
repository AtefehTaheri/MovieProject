package ir.atefehtaheri.movieapp.feature.homescreen.uistate

import ir.atefehtaheri.movieapp.core.common.models.MediaType

data class HomeUiState(
    val movies: Map<MediaType.Movie, PagerState> = mapOf(
        MediaType.Movie.UPCOMING to PagerState(),
        MediaType.Movie.TOP_RATED to PagerState(),
        MediaType.Movie.NOW_PLAYING to PagerState()
    )

    )
internal val HomeUiState.errorMessage
    get() = movies.values
        .map { it.errorMessage }
        .firstOrNull { it != null }