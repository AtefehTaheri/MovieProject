package ir.atefehtaheri.movieapp.feature.detailscreen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.MovieDetailDataModel
import ir.atefehtaheri.movieapp.feature.detailscreen.DetailScreenViewModel
import ir.atefehtaheri.movieapp.feature.detailscreen.uistate.DetailState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun DetailMovie(
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
) {
    val detailState by detailScreenViewModel.detailMovie.collectAsStateWithLifecycle()
    if (detailState.errorMessage != null) {
        ShowError(detailState.errorMessage!!)
    } else {



        DetailMovieScreen(modifier, detailState) {
            detailState.MovieDetailDataModel?.let {
                if (it.isFavorite) {
                    detailScreenViewModel.removeFavoriteList(Type.Movie,it.id)
                }else{
                    detailScreenViewModel.addToFavoriteList(Type.Movie)
                }
            }
        }
    }
}

@Composable
private fun DetailMovieScreen(
    modifier: Modifier = Modifier,
    detailState: DetailState,
    clickOnFavorite:()->Unit
) {

    when {
        detailState.loading -> LoadingState(modifier)
        else -> ShowListState(modifier, detailState.MovieDetailDataModel!!,clickOnFavorite)
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {
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

@Composable
private fun ShowListState(
    modifier: Modifier = Modifier,
    movieDetailDataModel: MovieDetailDataModel,
    clickOnFavorite:()->Unit
) {

    BoxWithConstraints {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .verticalScroll(state = scrollState)
        ) {

            HeaderScreen(
                movieDetailDataModel.poster_path,
                movieDetailDataModel.title,
                movieDetailDataModel.status,
                movieDetailDataModel.images,
                movieDetailDataModel.isFavorite,
                clickOnFavorite = clickOnFavorite
            )
            MovieMetadataView(movieDetailDataModel)

            Column(modifier = Modifier.height(screenHeight)) {
                InformationTabView(scrollState, movieDetailDataModel)
            }
        }
    }
}

@Composable
private fun MovieMetadataView(movieDetailDataModel: MovieDetailDataModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Icon(
                Icons.Filled.DateRange,
                contentDescription = stringResource(id = R.string.release_date),
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = movieDetailDataModel.release_date.split("-")[0],
                color = MaterialTheme.colorScheme.primary
            )
        }

        Divider(
            modifier = Modifier
                .height(20.dp)
                .width(2.dp),
            color = Color.White
        )


        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = stringResource(id = R.string.vote),
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = String.format("%.1f", movieDetailDataModel.vote_average),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Divider(
            color = Color.White, modifier = Modifier
                .height(20.dp)
                .width(2.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Rounded.Timer,
                contentDescription = stringResource(id = R.string.runtime),
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = "${movieDetailDataModel.runtime} minutes",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun InformationTabView(scrollState: ScrollState, movieDetailDataModel: MovieDetailDataModel) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val tabs = remember { InformationTabs.entries }
    val pagerState = rememberPagerState(pageCount = tabs::size)
    val selectedTabIndex = pagerState.currentPage


//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    val pagerState = rememberPagerState { InformationTabs.entries.size }
//
//    LaunchedEffect(key1 = selectedTabIndex) {
//        pagerState.animateScrollToPage(selectedTabIndex)
//    }
//
//    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
//        if (!pagerState.isScrollInProgress)
//            selectedTabIndex = pagerState.currentPage
//    }


    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(90)),
        contentColor = MaterialTheme.colorScheme.tertiaryContainer,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        selectedTabIndex = selectedTabIndex,
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
                selectedContentColor = MaterialTheme.colorScheme.scrim,
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
                }
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxHeight()
            .nestedScroll(remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        return if (available.y > 0) Offset.Zero else Offset(
                            x = 0f,
                            y = -scrollState.dispatchRawDelta(-available.y)
                        )
                    }
                }
            }
            )
    ) { page: Int ->
        when (page) {
            0 -> InformationMovieScreen(
                movieDetailDataModel.overview,
                movieDetailDataModel.genres
            )
            1 -> CreditsScreen(movieDetailDataModel.credits)
        }
    }
}
enum class InformationTabs(val text: String) {
    About("About"),
    Credits("Credits")
}