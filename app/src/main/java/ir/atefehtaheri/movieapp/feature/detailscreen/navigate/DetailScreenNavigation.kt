package ir.atefehtaheri.movieapp.feature.detailscreen.navigate

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.feature.detailscreen.DetailScreen

const val DetailScreenRoute = "detailscreen_route?id={id}&type={type}"


fun NavController.navigateToDetailScreen(
    type: String,
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate(
        DetailScreenRoute
            .replace("{id}", id)
            .replace("{type}", type), navOptions
    )
}

fun NavGraphBuilder.detailscreenDestination() {

    composable(route = DetailScreenRoute,
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("type") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        val id = it.arguments!!.getString("id")!!
        val type = it.arguments!!.getString("type")!!
        DetailScreen(type, id)
    }
}
