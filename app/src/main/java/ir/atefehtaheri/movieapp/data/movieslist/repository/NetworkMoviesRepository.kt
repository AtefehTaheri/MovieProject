package ir.atefehtaheri.movieapp.data.movieslist.repository

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.remote.MoviesDatasource
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.asMovieListDataModel
import javax.inject.Inject

class NetworkMoviesRepository @Inject constructor(
    private val moviesDatasource: MoviesDatasource,
) : MoviesRepository {

    override suspend fun getFirstPageMoviesPager(mediaType: MediaType.Movie): ResultStatus<List<MovieDataModel>> {

        return when (val result = moviesDatasource.getFirstPageMoviesPager(mediaType)) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asMovieListDataModel())
        }
    }

}