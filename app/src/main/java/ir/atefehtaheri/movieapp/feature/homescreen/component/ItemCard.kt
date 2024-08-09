package ir.atefehtaheri.movieapp.feature.homescreen.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.BASE_URL
import ir.atefehtaheri.movieapp.core.designsystem.component.shimmerEffect
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel


@Composable
fun ItemCard(
    movieItem: MovieDataModel?,
    loading: Boolean = true,
    onItemClick: () -> Unit = {}) {

    ElevatedCard(
        modifier = Modifier
            .width(
                dimensionResource(id = R.dimen.itemcard_width)
            )
            .aspectRatio(1 / 1.5f)
            .clickable {
                movieItem?.let {
                    onItemClick()
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_Elevation)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
    ) {
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )

        } else {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                )
            {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(BASE_URL + movieItem!!.poster_path))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    fallback = painterResource(R.drawable.placeholder),
                    contentDescription = stringResource(id = R.string.item_card),
                    modifier = Modifier.fillMaxSize(),

                    contentScale = ContentScale.FillBounds
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
                        .padding(3.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )

                    Text(
                        text = String.format("%.1f", movieItem!!.vote_average),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(10.dp),
                text = movieItem!!.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
            )
        }
    }
}
