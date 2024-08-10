package ir.atefehtaheri.movieapp.data.detailitem.repository


import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.remote.DetailItemDatasource
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.MovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.TvShowDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.asMovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.asTvShowDetailDataModel
import javax.inject.Inject

class NetworkDetailItemRepository @Inject constructor(
    private val DetailItemDatasource: DetailItemDatasource,
    ) : DetailItemRepository {
    override suspend fun getDetailMovie(movieid:String): ResultStatus<MovieDetailDataModel> {
        return when (val result = DetailItemDatasource.getDetailMovie(movieid)) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asMovieDetailDataModel())
        }
    }
    override suspend fun getDetailTvShow(tvshowid:String): ResultStatus<TvShowDetailDataModel> {
        return when (val result = DetailItemDatasource.getDetailTvShow(tvshowid)) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asTvShowDetailDataModel())
        }
    }
}