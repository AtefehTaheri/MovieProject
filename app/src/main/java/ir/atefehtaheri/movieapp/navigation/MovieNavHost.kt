package ir.atefehtaheri.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.omidtaheri.template.ui.MovieAppState
import ir.atefehtaheri.movieapp.core.common.models.AppContentType
import ir.atefehtaheri.movieapp.core.common.models.AppNavigationType
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.HomeScreenRoute
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.homeScreenDestination
import ir.atefehtaheri.movieapp.feature.searchscreen.navigation.searchScreenDestination

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
        homeScreenDestination()
        searchScreenDestination()
    }

}