package ir.atefehtaheri.movieapp.data.detailitem.repository


import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.remote.DetailItemDatasource
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.MovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.TvShowDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.asMovieDetailDataModel
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.asTvShowDetailDataModel
import ir.atefehtaheri.movieapp.data.favoritelist.local.FavoriteListDatasource
import javax.inject.Inject

class NetworkDetailItemRepository @Inject constructor(
    private val DetailItemDatasource: DetailItemDatasource,
    private val favoriteListDatasource: FavoriteListDatasource
) : DetailItemRepository {


    override suspend fun getDetailMovie(movieid: Int): ResultStatus<MovieDetailDataModel> {
        val result = DetailItemDatasource.getDetailMovie(movieid)
        val resultIsFavoriteMovie = favoriteListDatasource.isFavoriteMovie(movieid)
        return when (result) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)

            is ResultStatus.Success -> {
                when (resultIsFavoriteMovie) {
                    is ResultStatus.Failure -> ResultStatus.Failure(resultIsFavoriteMovie.exception_message)
                    is ResultStatus.Success -> ResultStatus.Success(
                        result.data?.asMovieDetailDataModel(
                            resultIsFavoriteMovie.data!!
                        )
                    )
                }
            }
        }
    }

    override suspend fun getDetailTvShow(tvshowid: Int): ResultStatus<TvShowDetailDataModel> {
        val result = DetailItemDatasource.getDetailTvShow(tvshowid)
        val resultIsFavoriteTvShow = favoriteListDatasource.isFavoriteMovie(tvshowid)

        return when (result) {
            is ResultStatus.Failure -> ResultStatus.Failure(result.exception_message)

            is ResultStatus.Success -> {
                when (resultIsFavoriteTvShow) {
                    is ResultStatus.Failure -> ResultStatus.Failure(resultIsFavoriteTvShow.exception_message)
                    is ResultStatus.Success -> ResultStatus.Success(
                        result.data?.asTvShowDetailDataModel(
                            resultIsFavoriteTvShow.data!!
                        )
                    )
                }
            }
        }
    }
}