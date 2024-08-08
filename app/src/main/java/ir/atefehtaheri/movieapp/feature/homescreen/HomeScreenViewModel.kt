package ir.atefehtaheri.movieapp.feature.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.nowplaying.repository.models.NowPlayingListDataModel
import ir.atefehtaheri.movieapp.data.topratedmovie.repository.models.TopRatedMovieListDataModel
import ir.atefehtaheri.movieapp.data.upcominglist.repository.models.UpcomingListDataModel
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.HomeUiState
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.PagerState
import ir.atefehtaheri.nowplaying.repository.NowPlayingRepository
import ir.atefehtaheri.topratedmovie.repository.TopRatedMovieRepository
import ir.atefehtaheri.upcominglist.repository.UpcomingListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
//    private val nowPlayingRepository: NowPlayingRepository,
    private val upcomingListRepository: UpcomingListRepository,
//    private val topRatedMovieRepository: TopRatedMovieRepository

) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
//        getNowPlayingMovie()
        getUpcomingMovie()
//        getTopRatedMovie()
    }

//    private fun getNowPlayingMovie() {
//        viewModelScope.launch {
//            _uiState.update {
//                it.copy(NowPlayingPagerState = PagerState(null,
//                    true,
//                    null)
//                )
//            }
//            val response = nowPlayingRepository.getFirstPageNowPlaying()
//            when (response) {
//                is ResultStatus.Failure ->
//                    _uiState.update {
//                        it.copy(NowPlayingPagerState = PagerState(null,
//                            false,
//                            response.exception_message)
//                        )
//                    }
//                is ResultStatus.Success -> {
//                    _uiState.update {
//                        it.copy(
//                            NowPlayingPagerState = PagerState(
//                                NowPlayingListDataModel(response.data?.nowplaying),
//                                false,
//                                null
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }


    private fun getUpcomingMovie() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    UpcomingPagerState = PagerState(null,
                        true,
                        null)
                )
            }
            val response = upcomingListRepository.getFirstPageUpcomingList()
            when (response) {
                is ResultStatus.Failure ->
                    _uiState.update {
                        it.copy(
                            UpcomingPagerState = PagerState(null,
                                false,
                                response.exception_message)
                        )
                    }
                is ResultStatus.Success -> {
                    _uiState.update {
                        it.copy(
                            UpcomingPagerState = PagerState(
                                UpcomingListDataModel(response.data?.upcominglist),
                                false,
                                null
                            )
                        )
                    }
                }
            }
        }
    }

//
//    private fun getTopRatedMovie() {
//        viewModelScope.launch {
//
//            _uiState.update {
//                it.copy(
//                    TopRatedMoviePagerState = PagerState(null,
//                        true,
//                        null)
//                )
//            }
//
//            val response = topRatedMovieRepository.getFirstPageTopRatedMovie()
//            when (response) {
//                is ResultStatus.Failure ->
//                    _uiState.update {
//                        it.copy(
//                            TopRatedMoviePagerState = PagerState(null,
//                                false,
//                                response.exception_message)
//                        )
//                    }
//                is ResultStatus.Success -> {
//                    _uiState.update {
//                        it.copy(
//                            TopRatedMoviePagerState = PagerState(
//                                TopRatedMovieListDataModel(response.data?.topRatedMovieList),
//                                false,
//                                null)
//                        )
//                    }
//                }
//            }
//        }
//    }
}

