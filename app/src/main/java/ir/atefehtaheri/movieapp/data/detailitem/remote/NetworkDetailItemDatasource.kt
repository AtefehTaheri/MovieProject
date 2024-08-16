package ir.atefehtaheri.movieapp.data.detailitem.remote

import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.remote.api.DetailItemApi
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.movie.MovieDetailDto
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.tvshow.TvShowDetailDto
import ir.atefehtaheri.network.NetworkResponse
import javax.inject.Inject

class NetworkDetailItemDatasource @Inject constructor(
    private val detailItemApi: DetailItemApi,
) : DetailItemDatasource {


    override suspend fun getDetailMovie(movieid: Int): ResultStatus<MovieDetailDto> {
        return when (val result = detailItemApi.getDetailMovie(movieid)) {
            is NetworkResponse.ApiError -> ResultStatus.Failure(result.body.status_message)
            is NetworkResponse.NetworkError -> ResultStatus.Failure(
                result.error.message ?: "NetworkError"
            )

            is NetworkResponse.Success -> ResultStatus.Success(result.body)
            is NetworkResponse.UnknownError -> ResultStatus.Failure(
                result.error.message ?: "UnknownError"
            )
        }
    }

    override suspend fun getDetailTvShow(tvshowid: Int): ResultStatus<TvShowDetailDto> {
        return when (val result = detailItemApi.getDetailTvShow(tvshowid)) {
            is NetworkResponse.ApiError -> ResultStatus.Failure(result.body.status_message)
            is NetworkResponse.NetworkError -> ResultStatus.Failure(
                result.error.message ?: "NetworkError"
            )

            is NetworkResponse.Success -> ResultStatus.Success(result.body)
            is NetworkResponse.UnknownError -> ResultStatus.Failure(
                result.error.message ?: "UnknownError"
            )
        }
    }

}