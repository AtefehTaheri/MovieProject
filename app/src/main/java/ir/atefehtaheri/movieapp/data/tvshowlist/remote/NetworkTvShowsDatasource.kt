package ir.atefehtaheri.movieapp.data.tvshowlist.remote


import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.api.TvShowApi
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.models.TvShowsDto
import ir.atefehtaheri.network.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkTvShowsDatasource @Inject constructor(
    private val tvShowApi: TvShowApi,
    private val dispatcher: CoroutineDispatcher,
) : TvShowsDatasource {


    override suspend fun getTvShowPager(
        mediaType: MediaType.TvShow,
        page: Int
    ): ResultStatus<TvShowsDto> {

        return withContext(dispatcher) {
            ensureActive()
            val result = when (mediaType) {
                MediaType.TvShow.TOP_RATED -> tvShowApi.getTopRatedTvShowList(page = page)
                MediaType.TvShow.Airing -> tvShowApi.getTvAiringList(page = page)
            }

            when (result) {
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

    override suspend fun getSearchTvShowPager(query: String, page: Int): ResultStatus<TvShowsDto> {
        return withContext(dispatcher) {
            ensureActive()
            val result = tvShowApi.getSearchTvShowList(page = page, query = query)
            when (result) {
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
}
