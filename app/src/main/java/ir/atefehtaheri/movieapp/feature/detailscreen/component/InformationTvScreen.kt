package ir.atefehtaheri.movieapp.feature.detailscreen.component


import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.BASE_URL
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.Episode
import ir.atefehtaheri.movieapp.data.detailitem.repository.models.TvShowDetailDataModel


@Composable
fun InformationTvScreen(
    tvShowDetailDataModel: TvShowDetailDataModel
) {
    var expandedState by remember { mutableStateOf(false) }
    val scrollstate = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollstate)
            .padding(16.dp)
    ) {

        if (tvShowDetailDataModel.overview == "") {
            Text(
                text = stringResource(id = R.string.no_description),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        } else {
            Text(
                text = tvShowDetailDataModel.overview,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = if (expandedState) 9 else 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.clickable { expandedState = !expandedState },
                text = if (expandedState)
                    stringResource(id = R.string.Read_less)
                else
                    stringResource(id = R.string.Read_more),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedVisibility(visible = tvShowDetailDataModel.genres.size != 0) {
            Column {
                Text(
                    text = stringResource(id = R.string.genres_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(tvShowDetailDataModel.genres) {
                        SuggestionChip(
                            onClick = { },
                            label = { Text(it) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                        )
                    }
                }
            }
        }
        TvShowMetadataView(tvShowDetailDataModel)
        Text(
            text = stringResource(id = R.string.last_episode_to_air),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.height(5.dp))

        EpisodeItem(tvShowDetailDataModel.last_episode_to_air)

        Spacer(modifier = Modifier.height(10.dp))
        tvShowDetailDataModel.next_episode_to_air?.let {
            Text(
                text = stringResource(id = R.string.next_episode_to_air),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            Spacer(modifier = Modifier.height(5.dp))
            EpisodeItem(it)
        }
    }
}


@Composable
private fun EpisodeItem(episode: Episode) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
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

            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(BASE_URL + episode.still_path) ?: "")
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
                        contentDescription = stringResource(id = R.string.vote),
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )

                    Text(
                        text = String.format("%.1f", episode.vote_average),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
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
                    text = episode.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "${stringResource(id = R.string.episode_number)} ${episode.episode_number}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${stringResource(id = R.string.season_number)} ${episode.season_number}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                )
            }
        }
    }
}


@Composable
private fun TvShowMetadataView(tvShowDetailDataModel: TvShowDetailDataModel) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${stringResource(id = R.string.first_air_date)} ${tvShowDetailDataModel.first_air_date}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.last_air_date)} ${tvShowDetailDataModel.last_air_date}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.number_of_seasons)}  ${tvShowDetailDataModel.number_of_seasons}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.number_of_episodes)}  ${tvShowDetailDataModel.number_of_episodes}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}