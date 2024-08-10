package ir.atefehtaheri.movieapp.feature.detailscreen.uistate

import ir.atefehtaheri.movieapp.data.detailitem.repository.models.MovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.TvShowDetailDataModel


data class DetailState(
    val MovieDetailDataModel: MovieDetailDataModel?=null,
    val tvShowDetailDataModel: TvShowDetailDataModel? = null,
    val loading: Boolean = true,
    val errorMessage: String? = null
)
