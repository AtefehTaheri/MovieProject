package ir.atefehtaheri.movieapp.data.detailitem.repository

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.MovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.TvShowDetailDataModel

interface DetailItemRepository {

    suspend fun getDetailMovie(movieid:Int): ResultStatus<MovieDetailDataModel>
    suspend fun getDetailTvShow(tvshowid:Int): ResultStatus<TvShowDetailDataModel>


}