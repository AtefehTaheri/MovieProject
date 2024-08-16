package ir.atefehtaheri.movieapp.feature.searchscreen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.searchscreen.SearchScreenRoute


const val SearchScreenRoute = "searchscreen_route"


fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null) {
    this.navigate(SearchScreenRoute, navOptions)
}

fun NavGraphBuilder.searchScreenDestination(
    onItemClick: (Type, Int, NavOptions?) -> Unit
) {

    composable(route = SearchScreenRoute) {
        SearchScreenRoute(
            onItemClick = onItemClick
        )
    }
}
