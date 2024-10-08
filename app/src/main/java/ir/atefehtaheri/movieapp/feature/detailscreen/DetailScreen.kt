package ir.atefehtaheri.movieapp.feature.detailscreen


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailMovie
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailTvShow


@Composable
internal fun DetailScreen(
    type: Type,
    id: Int? = null,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()

) {
    id?.let {
        when (type) {

            Type.Movie -> {
                detailScreenViewModel.getDetailMovie(id)
                DetailMovie()
            }

            Type.TvShow -> {
                detailScreenViewModel.getDetailTvShow(id)
                DetailTvShow()
            }
        }
    }
}
