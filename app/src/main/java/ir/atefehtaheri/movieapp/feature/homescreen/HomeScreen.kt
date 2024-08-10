package ir.atefehtaheri.movieapp.feature.homescreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import ir.atefehtaheri.homescreen.components.UpcomingPager
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.feature.homescreen.component.HorizontalItemList
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.errorMessage

@Composable
fun HomeScreen(
    navToUpcoming: (NavOptions?) -> Unit={},
    navToNowPlaying: (NavOptions?) -> Unit = {},
    navToTopRated: (NavOptions?) -> Unit = {},
    onItemClick: (String, String, NavOptions?) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

) {
    val scrollstate = rememberScrollState()
    val homeUiState by homeScreenViewModel.uiState.collectAsStateWithLifecycle()

    if (homeUiState.errorMessage != null) {
        ShowError(homeUiState.errorMessage!!)

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollstate)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            Header(
                R.string.upcoming_movies
            ) { navToUpcoming(null) }
            UpcomingPager(
                onItemClick,
                homeUiState.movies.get(MediaType.Movie.UPCOMING)!!
            )
            Header(
                R.string.now_playing
            ) { navToNowPlaying(null) }
            HorizontalItemList(
                homeUiState.movies.get(MediaType.Movie.NOW_PLAYING)!!,
                MediaType.Movie.NOW_PLAYING,
                onItemClick
            )
            HorizontalItemList(
                homeUiState.tvShows.get(MediaType.TvShow.Airing)!!,
                MediaType.TvShow.Airing,
                onItemClick,

            )
            Header(
                R.string.top_rated
            )
            { navToTopRated(null) }

            HorizontalItemList(
                homeUiState.movies.get(MediaType.Movie.TOP_RATED)!!,
                MediaType.Movie.TOP_RATED,
                onItemClick
            )
            HorizontalItemList(
                homeUiState.tvShows.get(MediaType.TvShow.TOP_RATED)!!,
                MediaType.TvShow.TOP_RATED,
                onItemClick
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}

@Composable
fun Header(
    @StringRes title: Int,
    navToShowList: (NavOptions?) -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(
                    id = R.dimen.header_padding_horizontal
                ),
                vertical = dimensionResource(
                    id = R.dimen.header_padding_vertical
                ),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Text(
            modifier = Modifier.clickable { navToShowList(null) },
            text = stringResource(id = R.string.showall),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}
