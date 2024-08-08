package ir.atefehtaheri.movieapp.feature.homescreen.uistate

import ir.atefehtaheri.movieapp.data.nowplaying.repository.models.NowPlayingListDataModel
import ir.atefehtaheri.movieapp.data.topratedmovie.repository.models.TopRatedMovieListDataModel
import ir.atefehtaheri.movieapp.data.upcominglist.repository.models.UpcomingListDataModel

data class HomeUiState(
    val NowPlayingPagerState: PagerState<NowPlayingListDataModel> = PagerState(),
    val TopRatedMoviePagerState: PagerState<TopRatedMovieListDataModel> = PagerState(),
    val UpcomingPagerState: PagerState<UpcomingListDataModel> = PagerState()

)
internal val HomeUiState.errorMessage: String?
    get() = listOf(NowPlayingPagerState, TopRatedMoviePagerState, UpcomingPagerState)
        .mapNotNull { it.errorMessage }
        .firstOrNull()