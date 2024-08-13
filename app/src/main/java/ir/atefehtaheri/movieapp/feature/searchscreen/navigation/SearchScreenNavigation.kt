package ir.atefehtaheri.movieapp.feature.searchscreen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.feature.searchscreen.SearchScreenRoute


const val SearchScreenRoute = "searchscreen_route"


fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null) {
    this.navigate("search", navOptions)
}

fun NavGraphBuilder.searchScreenDestination(
    onItemClick: (MediaType, String, NavOptions?) -> Unit
) {

    composable(route = SearchScreenRoute) {
        SearchScreenRoute(
            onItemClick = onItemClick
        )
    }
}
