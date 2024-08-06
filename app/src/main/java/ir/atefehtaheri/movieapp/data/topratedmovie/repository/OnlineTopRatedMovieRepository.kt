package ir.atefehtaheri.topratedmovie.repository



import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.topratedmovie.repository.models.TopRatedMovieListDataModel
import ir.atefehtaheri.movieapp.data.topratedmovie.repository.models.asTopRatedMovieListDataModel
import ir.atefehtaheri.topratedmovie.remote.TopRatedMovieDatasource
import javax.inject.Inject

class OnlineTopRatedMovieRepository @Inject constructor(
    private val topRatedMovieDatasource: TopRatedMovieDatasource,

    ) : TopRatedMovieRepository {
    override suspend fun getFirstPageTopRatedMovie(): ResultStatus<TopRatedMovieListDataModel> {
        return when (val result = topRatedMovieDatasource.getFirstPageTopRatedMovie()) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)
            is ResultStatus.Success -> ResultStatus.Success(result.data?.asTopRatedMovieListDataModel())
        }
    }
}