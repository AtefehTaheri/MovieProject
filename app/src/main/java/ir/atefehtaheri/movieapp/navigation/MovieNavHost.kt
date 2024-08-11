package ir.atefehtaheri.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.omidtaheri.template.ui.MovieAppState
import ir.atefehtaheri.movieapp.feature.detailscreen.navigate.detailscreenDestination
import ir.atefehtaheri.movieapp.feature.detailscreen.navigate.navigateToDetailScreen
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.HomeScreenRoute
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.homeScreenDestination
import ir.atefehtaheri.movieapp.feature.upcominglistScreen.movieListDestination

@Composable
fun MovieNavHost(
    appState: MovieAppState,
    startDestination: String =HomeScreenRoute,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreenDestination(onItemClick= navController::navigateToDetailScreen)

        detailscreenDestination()
        movieListDestination(onItemClick= navController::navigateToDetailScreen)
    }

}