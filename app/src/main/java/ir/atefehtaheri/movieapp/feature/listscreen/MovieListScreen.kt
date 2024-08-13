package ir.atefehtaheri.movieapp.feature.listscreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
internal fun MovieListRoute(
    onItemClick: (MediaType, id: String, NavOptions?) -> Unit,
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel(),
    mediaTypeMovie: MediaType.Movie,
    mediaTypeTvShow: MediaType.TvShow

) {
    LaunchedEffect(key1 = Unit) {
        movieViewModel.getDataMovie(mediaTypeMovie)
        movieViewModel.getDataTvShow(mediaTypeTvShow)
    }

    val uiState by movieViewModel.uiState.collectAsStateWithLifecycle()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvShows = uiState.tvShows.collectAsLazyPagingItems()
    when (mediaTypeMovie) {
        MediaType.Movie.UPCOMING -> UpcomingListScreen(movies, onItemClick, modifier)
        else -> MovieListScreen(movies, tvShows, onItemClick, modifier)
    }
}

@Composable
fun MovieListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    tvShows: LazyPagingItems<MovieDataModel>,
    onItemClick: (MediaType, String, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        movies.loadState.refresh is LoadState.Error -> ShowError(
            (movies.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        tvShows.loadState.refresh is LoadState.Error -> ShowError(
            (tvShows.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        movies.loadState.refresh is LoadState.Loading -> LoadingState()
        tvShows.loadState.refresh is LoadState.Loading -> LoadingState()



        else -> ShowListScreen(movies, tvShows, onItemClick, modifier)
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    tvshow: LazyPagingItems<MovieDataModel>,
    onItemClick: (MediaType, String, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {


    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val tabs = remember { InformationTabs.entries }
    val pagerState = rememberPagerState(pageCount = tabs::size)
    val selectedTabIndex = pagerState.currentPage

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {


        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp)

        ) { index ->
            when (index) {
                0 -> PageContent_Movie(movies, onItemClick)
                1 -> PageContent_Tvshow(tvshow, onItemClick)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {

            TabRow(selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
                    .clip(RoundedCornerShape(90)),
                indicator = { _ ->
                    Box {}
                }
            ) {
                tabs.forEachIndexed { index, currentTab ->
                    Tab(
                        modifier = if (selectedTabIndex == index) Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        else Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                MaterialTheme.colorScheme.tertiaryContainer
                            ),


                        selected = selectedTabIndex == index,
                        selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = currentTab.text,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                    )
                }
            }
        }
    }
}


private enum class InformationTabs(val text: String) {
    Movie("Movie"),
    TvShow("TvShow")
}

@Composable
private fun PageContent_Movie(
    movies: LazyPagingItems<MovieDataModel>,
    onItemClick: (MediaType, String, NavOptions?) -> Unit
) {
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier, state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }

            items(
                count = movies.itemCount,
            ) { index ->
                val item = movies[index]

                if (item != null) {
                    MovieItemView(
                        item,
                        onItemClick
                    )
                }
            }
            item {
                if (movies.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
private fun PageContent_Tvshow(
    tvshow: LazyPagingItems<MovieDataModel>,
    onItemClick: (MediaType, String, NavOptions?) -> Unit
) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier, state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
            items(
                count = tvshow.itemCount,
            ) { index ->
                val item = tvshow[index]

                if (item != null) {
                    MovieItemView(
                        item,
                        onItemClick
                    )
                }
            }
            item {
                if (tvshow.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

