package ir.atefehtaheri.movieapp.feature.searchscreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.core.designsystem.component.ShowError
import ir.atefehtaheri.movieapp.data.movieslist.repository.models.MovieDataModel
import ir.atefehtaheri.movieapp.feature.listscreen.LoadingState
import ir.atefehtaheri.movieapp.feature.listscreen.ShowListScreen

@Composable
internal fun SearchScreenRoute(
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {


    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvShows = uiState.tvShows.collectAsLazyPagingItems()
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()

    SearchScreen(
        movies,
        tvShows,
        searchQuery,
        uiState.isSearching,
        searchViewModel::updateQuery,
        onItemClick
    )

}

@Composable
fun SearchScreen(
    movies: LazyPagingItems<MovieDataModel>,
    tvShows: LazyPagingItems<MovieDataModel>,
    searchQuery: String,
    isSearching: Boolean = false,
    updateQuery: (String) -> Unit,
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    modifier: Modifier = Modifier

) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                softwareKeyboardController?.hide()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = searchQuery,
            onValueChange = { updateQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 15.dp),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                disabledContainerColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.scrim,
                unfocusedTextColor = MaterialTheme.colorScheme.scrim,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardActions = KeyboardActions(
                onDone = {
                    softwareKeyboardController?.hide()
                }
            ),
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(id = R.string.text_placeholder),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            trailingIcon = {

                IconButton(onClick = { softwareKeyboardController?.hide() }) {
                    Icon(
                        Icons.Filled.Search,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = stringResource(id = R.string.search_button),
                    )
                }

            }
        )
        AnimatedVisibility(visible = !isSearching) {
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
                    text = stringResource(id = R.string.empty_state_search),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }

        }

        AnimatedVisibility(visible = isSearching) {
            when {
                movies.loadState.refresh is LoadState.Error -> ShowError(
                    (movies.loadState.refresh as LoadState.Error).error.message ?: ""
                )

                tvShows.loadState.refresh is LoadState.Error -> ShowError(
                    (tvShows.loadState.refresh as LoadState.Error).error.message ?: ""
                )

                movies.isLoading() -> LoadingState(modifier)
                tvShows.isLoading() -> LoadingState(modifier)

                else -> ShowListScreen(movies, tvShows, onItemClick, modifier)
            }
        }
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.isLoading(): Boolean {
    return this.loadState.refresh is LoadState.Loading ||
            (this.loadState.append !is LoadState.Loading && this.itemCount == 0 &&
                    !this.loadState.append.endOfPaginationReached)
}


