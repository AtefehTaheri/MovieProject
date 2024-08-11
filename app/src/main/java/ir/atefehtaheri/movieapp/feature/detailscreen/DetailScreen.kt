package ir.atefehtaheri.movieapp.feature.detailscreen


import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailMovie
import ir.atefehtaheri.movieapp.feature.detailscreen.component.DetailTvShow


@Composable
internal fun DetailScreen(
    type: Type,
    id: String,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()

) {

    when (type) {

        Type.Movie ->  {
            detailScreenViewModel.getDetailMovie(id)
            DetailMovie()
        }
        Type.TvShow -> {
            detailScreenViewModel.getDetailTvShow(id)
            DetailTvShow()
        }
    }
}
