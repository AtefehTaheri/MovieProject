package ir.atefehtaheri.movieapp.feature.detailscreen


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailMovie
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailTvShow


@Composable
internal fun DetailScreen(
    type: String,
    id: String,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()

) {
    when (type) {

        MediaType.Movie.name -> {
            detailScreenViewModel.getDetailMovie(id)
            DetailMovie()
        }

        MediaType.TvShow.name -> {
            detailScreenViewModel.getDetailTvShow(id)
            DetailTvShow()
        }

    }
}
