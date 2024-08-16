package ir.atefehtaheri.movieapp.data.movieslist.remote


import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.api.MovieApi
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto
import ir.atefehtaheri.network.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkMoviesDatasource @Inject constructor(
    private val movieApi: MovieApi,
    private val dispatcher: CoroutineDispatcher,
) : MoviesDatasource {


    override suspend fun getMoviesPager(
        mediaType: MediaType.Movie,
        page: Int
    ): ResultStatus<MoviesDto> {
        return withContext(dispatcher) {
            ensureActive()
            val result = when (mediaType) {
                MediaType.Movie.UPCOMING -> movieApi.getUpcomingList(page = page)
                MediaType.Movie.TOP_RATED -> movieApi.getTopRatedMovieList(page = page)
                else -> movieApi.getNowPlaying(page = page)
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

    override suspend fun getSearchMoviesPager(
        query: String,
        page: Int
    ): ResultStatus<MoviesDto> {
        return withContext(dispatcher) {
            ensureActive()
            val result = movieApi.getSearchMovieList(page = page, query = query)

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