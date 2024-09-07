package ir.atefehtaheri.homescreen.components

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.BASE_URL
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.designsystem.component.shimmerEffect
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.feature.homescreen.uistate.PagerState


@Composable
internal fun UpcomingPager(
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    state: PagerState,
    modifier: Modifier = Modifier,

    ) {
    when {
        state.isLoading -> LoadingState(modifier)
        else -> ShowListState(state.listDataModel, onItemClick,modifier)
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2 / 1f)
            .padding(10.dp)
            .shimmerEffect()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowListState(
    upcominglist: List<MovieDataModel>?,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {

    upcominglist?.let {

        val pagerState = rememberPagerState(pageCount = { upcominglist.size })
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(id = R.dimen.card_Elevation)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            ) {
                HorizontalPager(
                    modifier = Modifier,
                    state = pagerState
                ) { page ->
                    val image = upcominglist[page].backdrop_path
                    Box {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Uri.parse(BASE_URL + image) ?: "")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.placeholder),
                            fallback = painterResource(R.drawable.placeholder),
                            contentDescription = stringResource(id = R.string.upcoming_movie_image),
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(2 / 1f)
                                .clickable {
                                    onItemClick(
                                        upcominglist[page].media_type.type,
                                        upcominglist[page].id,
                                        null
                                    )
                                },
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
                                .padding(10.dp),
                            textAlign = TextAlign.Center,
                            text = upcominglist[page].title,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                }

            }
            DotsIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                dotCount = upcominglist.size,
                type = ShiftIndicatorType(
                    dotsGraphic = DotGraphic(
                        12.dp,
                        borderWidth = 0.dp,
                        borderColor = Color.Black,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    shiftSizeFactor = 3f
                ),
                dotSpacing = 5.dp,
                pagerState = pagerState
            )

        }

    }
}

