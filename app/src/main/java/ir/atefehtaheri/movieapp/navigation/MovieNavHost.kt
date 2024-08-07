package ir.atefehtaheri.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.omidtaheri.template.ui.MovieAppState

@Composable
fun MovieNavHost(
    appState: MovieAppState,
    startDestination: String ="",
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

    }

}