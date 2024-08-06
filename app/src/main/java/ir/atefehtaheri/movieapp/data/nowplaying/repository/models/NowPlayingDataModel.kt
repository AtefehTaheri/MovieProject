package ir.atefehtaheri.movieapp.data.nowplaying.repository.models

import ir.atefehtaheri.nowplaying.remote.models.Movie
import ir.atefehtaheri.nowplaying.remote.models.NowPlayingDto

data class NowPlayingListDataModel(
    val nowplaying: List<NowPlayingDataModel>? = null,
)

data class NowPlayingDataModel(
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double
)


fun NowPlayingDto.asNowPlayingListDataModel(): NowPlayingListDataModel {
    return NowPlayingListDataModel(
        nowplaying = this.results.map {
            it.asNowPlayingDataModel()
        }
    )

}

fun Movie.asNowPlayingDataModel(): NowPlayingDataModel {
    return NowPlayingDataModel(
        backdrop_path = backdrop_path,
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average
    )
}
