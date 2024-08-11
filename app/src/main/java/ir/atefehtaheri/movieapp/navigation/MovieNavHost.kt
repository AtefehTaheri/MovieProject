package ir.atefehtaheri.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import ir.atefehtaheri.movieapp.feature.detailscreen.navigate.detailscreenDestination
import ir.atefehtaheri.movieapp.feature.detailscreen.navigate.navigateToDetailScreen
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.HomeScreenRoute
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.homeScreenDestination
import ir.atefehtaheri.movieapp.feature.upcominglistScreen.movieListDestination
import ir.atefehtaheri.movieapp.feature.upcominglistScreen.navigateToMovieList
import ir.atefehtaheri.movieapp.ui.MovieAppState

@Composable
fun MovieNavHost(
    appState: MovieAppState,
    startDestination: String = "home",
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {


        navigation(startDestination = HomeScreenRoute, route = "home") {
            homeScreenDestination(
                navToShowList = navController::navigateToMovieList,
                onItemClick = navController::navigateToDetailScreen
            )
            detailscreenDestination()
            movieListDestination(onItemClick = navController::navigateToDetailScreen)
        }

    }

}