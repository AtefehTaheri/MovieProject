package ir.atefehtaheri.movieapp.feature.favoritescreen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.favoritescreen.FavoriteListRoute

const val FavoriteScreenRoute = "favorite_route"


fun NavController.navigateToFavoriteScreen(navOptions: NavOptions? = null) {
    this.navigate(
        FavoriteScreenRoute,navOptions
    )
}

fun NavGraphBuilder.FavoriteScreenRoute( onItemClick: (Type, Int, NavOptions?) -> Unit) {

    composable(route = FavoriteScreenRoute) {
        FavoriteListRoute(onItemClick)
    }
}
