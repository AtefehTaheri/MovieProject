package ir.atefehtaheri.movieapp.data.movieslist.remote


import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.api.MovieApi
import ir.atefehtaheri.movieapp.data.movieslist.remote.models.MoviesDto
import ir.atefehtaheri.network.NetworkResponse
import javax.inject.Inject

class NetworkMoviesDatasource @Inject constructor(
    private val movieApi: MovieApi,
) : MoviesDatasource {


    override suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<MoviesDto> {


        val result = when (mediaType) {
            MediaType.Movie.UPCOMING -> movieApi.getUpcomingList()
            MediaType.Movie.TOP_RATED -> movieApi.getTopRatedMovieList()
            MediaType.Movie.NOW_PLAYING -> movieApi.getNowPlaying()
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