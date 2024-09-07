package ir.atefehtaheri.movieapp.feature.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel


@Composable
fun UpcomingListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {

    when {
        movies.loadState.refresh is LoadState.Error -> ShowError(
            (movies.loadState.refresh as LoadState.Error).error.message ?: ""
        )

        movies.loadState.refresh is LoadState.Loading -> LoadingState()
        else -> ShowListScreen(movies, onItemClick, modifier)
    }
}

@Composable
fun ShowListScreen(
    movies: LazyPagingItems<MovieDataModel>,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp), state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
}
