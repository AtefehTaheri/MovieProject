package ir.atefehtaheri.movieapp.feature.listscreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.BASE_URL
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel


@Composable
internal fun MovieItemView(
    movieDataModel : MovieDataModel,
    onItemClick:(MediaType, String, NavOptions?) -> Unit

    ) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(movieDataModel.media_type,
                    movieDataModel.id.toString(),
                    null)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 0.4f)
                .padding(10.dp)

        ) {

            Box ( modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ){

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(BASE_URL + movieDataModel.poster_path) ?: "")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    fallback = painterResource(R.drawable.placeholder),
                    contentDescription = stringResource(id = R.string.poster_placeholder),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                )
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
                    .padding(3.dp)
                    .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = stringResource(id = R.string.vote),
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )

                    Text(text = String.format("%.1f", movieDataModel.vote_average),
                        color = MaterialTheme.colorScheme.secondaryContainer)
                }
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movieDataModel.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = movieDataModel.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = stringResource(id = R.string.release_date),
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = movieDataModel.release_date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
            }
        }
    }
}



