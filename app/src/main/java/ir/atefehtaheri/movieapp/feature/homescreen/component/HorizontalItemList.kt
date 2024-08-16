package ir.atefehtaheri.movieapp.feature.homescreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.PagerState


@Composable
internal fun HorizontalItemList(
    state: PagerState,
    mediaType: Type,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 0.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            text = if (mediaType == Type.TvShow) "TvShows" else "Movies",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        when {
            state.isLoading -> LoadingState(modifier)
            else -> ShowListState(state.listDataModel,
                onItemClick)
        }

    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = dimensionResource(id = R.dimen.itemcard_width)
    val itemCount = (screenWidth / itemWidth).toInt()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemCount) {
            ItemCard(null, true)
        }
    }
}

@Composable
private fun ShowListState(
    listData: List<MovieDataModel>?,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    listData?.let { list ->
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(list) {
                ItemCard(it, false, onItemClick)
            }

        }
    }
}
