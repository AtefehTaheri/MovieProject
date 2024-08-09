package ir.atefehtaheri.movieapp.feature.homescreen.uistate

import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel


data class PagerState(
    val listDataModel: List<MovieDataModel>? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)