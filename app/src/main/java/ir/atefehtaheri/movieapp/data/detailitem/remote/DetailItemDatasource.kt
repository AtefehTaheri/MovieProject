package ir.atefehtaheri.movieapp.data.detailitem.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.movie.MovieDetailDto
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.tvshow.TvShowDetailDto

interface DetailItemDatasource {

    suspend fun getDetailMovie(movieid:String): ResultStatus<MovieDetailDto>
    suspend fun getDetailTvShow(tvshowid:String): ResultStatus<TvShowDetailDto>


}