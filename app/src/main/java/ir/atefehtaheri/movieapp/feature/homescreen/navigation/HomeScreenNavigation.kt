package ir.atefehtaheri.movieapp.feature.homescreen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.feature.homescreen.HomeScreen


const val HomeScreenRoute = "homescreen_route"


fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(HomeScreenRoute, navOptions)
}

fun NavGraphBuilder.homeScreenDestination(
//    navToUpcoming :  (NavOptions?) -> Unit,
//    navToNowPlaying :  (NavOptions?) -> Unit,
//    navToTopRated :  (NavOptions?) -> Unit,
    onItemClick:(MediaType, String, NavOptions?) -> Unit
   ) {

    composable(route = HomeScreenRoute) {
        HomeScreen (
//            navToUpcoming,
//            navToNowPlaying ,
//            navToTopRated,
            onItemClick= onItemClick
        )
    }
}
