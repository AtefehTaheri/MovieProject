package ir.atefehtaheri.movieapp.data.detailitem.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.movie.MovieDetailDto
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.tvshow.TvShowDetailDto

interface DetailItemDatasource {

    suspend fun getDetailMovie(movieid:Int): ResultStatus<MovieDetailDto>
    suspend fun getDetailTvShow(tvshowid:Int): ResultStatus<TvShowDetailDto>


}