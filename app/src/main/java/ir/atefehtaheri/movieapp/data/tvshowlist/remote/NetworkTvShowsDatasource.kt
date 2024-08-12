package ir.atefehtaheri.movieapp.data.tvshowlist.remote


import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.api.TvShowApi
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShowsDto
import ir.atefehtaheri.network.NetworkResponse
import javax.inject.Inject

class NetworkTvShowsDatasource @Inject constructor(
    private val tvShowApi: TvShowApi,
) : TvShowsDatasource {


    override suspend fun getTvShowPager(mediaType: MediaType.TvShow, page: Int): ResultStatus<TvShowsDto> {


        val result = when (mediaType) {
            MediaType.TvShow.TOP_RATED -> tvShowApi.getTopRatedTvShowList(page=page)
            MediaType.TvShow.Airing -> tvShowApi.getTvAiringList(page=page)
        }

        return when (result) {
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