package ir.atefehtaheri.movieapp.feature.listscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.AppContentType
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.feature.detailscreen.DetailScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
internal fun MovieListRoute(
    onItemClick: (Type, id: Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel(),
    mediaTypeMovie: MediaType.Movie,
    mediaTypeTvShow: MediaType.TvShow,
    displayFeatures: List<DisplayFeature>,
    contentType: AppContentType
) {

    movieViewModel.updateType(mediaTypeMovie, mediaTypeTvShow)
    val movies = movieViewModel.movies.collectAsLazyPagingItems()
    val tvShows = movieViewModel.tvShows.collectAsLazyPagingItems()

    when (contentType) {
        AppContentType.SINGLE_PANE -> {
            when (mediaTypeMovie) {
                MediaType.Movie.UPCOMING -> UpcomingListScreen(movies, onItemClick, modifier)
                else -> MovieListScreen(movies, tvShows, onItemClick, modifier)
            }
        }

        AppContentType.DUAL_PANE -> {
            LargeScreenListScreen(mediaTypeMovie,movies, tvShows, modifier, displayFeatures)
        }
    }
}

@Composable
fun LargeScreenListScreen(
    mediaTypeMovie: MediaType.Movie,
    movies: LazyPagingItems<MovieDataModel>,
    tvshow: LazyPagingItems<MovieDataModel>,
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>
) {

    when {
        movies.loadState.refresh is LoadState.Error -> ShowError(
            (movies.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        tvshow.loadState.refresh is LoadState.Error -> ShowError(
            (tvshow.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        movies.loadState.refresh is LoadState.Loading -> LoadingState(modifier)
        tvshow.loadState.refresh is LoadState.Loading -> LoadingState(modifier)


        else -> {
            var type by remember { mutableStateOf(Type.Movie) }
            var id by remember { mutableStateOf<Int?>(null) }
            if (movies.itemCount != 0) {
                id = movies.get(0)?.id
            }


            TwoPane(
                first = {
                    when(mediaTypeMovie){
                        MediaType.Movie.UPCOMING -> ShowListScreen(
                            movies,
                            { typeParam, idParam, _ ->
                                type = typeParam
                                id = idParam
                            },
                            modifier
                        )
                        else ->  ShowListScreen(
                            movies,
                            tvshow,
                            { typeParam, idParam, _ ->
                                type = typeParam
                                id = idParam
                            },
                            modifier
                        )
                    }
                },
                second = {
                    DetailScreen(type, id)
                },
                strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 5.dp),
                displayFeatures = displayFeatures
            )
        }
    }

}


@Composable
private fun MovieListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    tvShows: LazyPagingItems<MovieDataModel>,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        movies.loadState.refresh is LoadState.Error -> ShowError(
            (movies.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        tvShows.loadState.refresh is LoadState.Error -> ShowError(
            (tvShows.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        movies.loadState.refresh is LoadState.Loading -> LoadingState(modifier)
        tvShows.loadState.refresh is LoadState.Loading -> LoadingState(modifier)


        else -> ShowListScreen(movies, tvShows, onItemClick, modifier)
    }
}


@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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
fun ShowListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    tvshow: LazyPagingItems<MovieDataModel>,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {


    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val tabs = InformationTabs.entries
    val pagerState = rememberPagerState(pageCount = tabs::size)
    val selectedTabIndex = pagerState.currentPage
//    LaunchedEffect(pagerState.currentPage) {
//        selectedTabIndex = pagerState.currentPage
//    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
    ) {


        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
//                .fillMaxWidth()
//                .height(1000.dp)

        ) { index ->
            when (index) {
                0 -> PageContent_Movie(movies, onItemClick, Modifier.fillMaxSize())
                1 -> PageContent_Tvshow(tvshow, onItemClick, Modifier.fillMaxSize())
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
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {

        AnimatedVisibility(visible = movies.itemCount != 0) {

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
                item {
                    if (movies.loadState.append is LoadState.Error) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(id = R.string.append_error_message),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center )
                        }
                        
                    }
                }
            }
        }
        AnimatedVisibility(visible = movies.itemCount == 0) {

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = stringResource(id = R.string.not_found_image),
                    Modifier
                        .size(dimensionResource(id = R.dimen.erroe_image_size)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = stringResource(id = R.string.not_found_movie),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
private fun PageContent_Tvshow(
    tvshow: LazyPagingItems<MovieDataModel>,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        AnimatedVisibility(visible = tvshow.itemCount != 0) {
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
                item {
                    if (tvshow.loadState.append is LoadState.Error) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(id = R.string.append_error_message),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center )
                        }

                    }
                }
            }
        }
        AnimatedVisibility(visible = tvshow.itemCount == 0) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = stringResource(id = R.string.not_found_image),
                    Modifier
                        .size(dimensionResource(id = R.dimen.erroe_image_size)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = stringResource(id = R.string.not_found_tvshow),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

