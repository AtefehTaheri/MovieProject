package ir.atefehtaheri.movieapp.feature.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.ResultStatus
import ir.atefehtaheri.movieapp.data.detailitem.repository.DetailItemRepository
import ir.atefehtaheri.movieapp.feature.detailscreen.uistate.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailItemRepository: DetailItemRepository
    ) : ViewModel() {


    private val _detailMovie = MutableStateFlow(DetailState())
    val detailMovie = _detailMovie.asStateFlow()


    fun getDetailMovie(movieid:String) {
        viewModelScope.launch {

            _detailMovie.update {
                it.copy(null,
                    null,
                    true,
                    null
                )
            }
            val response = detailItemRepository.getDetailMovie(movieid)
            when (response) {
                is ResultStatus.Failure ->
                    _detailMovie.update {
                        it.copy(errorMessage = response.exception_message)
                    }
                is ResultStatus.Success -> {
                    _detailMovie.update {
                        it.copy(response.data,
                            null,
                            false,
                            null
                        )
                    }
                }
            }
        }
    }

    fun getDetailTvShow(tvshowid:String) {
        viewModelScope.launch {
            _detailMovie.update {
                it.copy(null,
                    null,
                    true,
                    null
                )
            }
            val response = detailItemRepository.getDetailTvShow(tvshowid)
            when (response) {
                is ResultStatus.Failure ->
                    _detailMovie.update {
                        it.copy(errorMessage = response.exception_message)
                    }

                is ResultStatus.Success -> {
                    _detailMovie.update {
                        it.copy(null,
                            response.data,
                            false,
                            null
                        )
                    }
                }
            }
        }
    }

}

