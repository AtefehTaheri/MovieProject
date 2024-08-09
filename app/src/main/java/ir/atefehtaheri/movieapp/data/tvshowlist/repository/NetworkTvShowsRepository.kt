package ir.atefehtaheri.movieapp.data.tvshowlist.repository

import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.TvShowsDatasource
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.models.asMovieListDataModel
import javax.inject.Inject

class NetworkTvShowsRepository @Inject constructor(
    private val tvShowsDatasource: TvShowsDatasource,
) : TvShowsRepository {

    override suspend fun getFirstPageTvShowPager(mediaType: MediaType.TvShow): ResultStatus<List<MovieDataModel>> {

        return when (val result = tvShowsDatasource.getFirstPageTvShowPager(mediaType)) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asMovieListDataModel())
        }
    }

}