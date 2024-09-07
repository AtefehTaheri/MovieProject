package ir.atefehtaheri.movieapp.feature.detailscreen.navigate

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.detailscreen.DetailScreen

const val DetailScreenRoute = "detailscreen_route?id={id}&type={type}"


fun NavController.navigateToDetailScreen(
    type: Type,
    id: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(
        DetailScreenRoute
            .replace("{id}", id.toString())
            .replace("{type}", type.name),
        navOptions
    )
}

fun NavGraphBuilder.detailscreenDestination() {

    composable(route = DetailScreenRoute,
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
            },
            navArgument("type") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        val id = it.arguments!!.getInt("id")!!
        val typeName = it.arguments!!.getString("type")!!
        val type = when (typeName) {
            Type.TvShow.name -> Type.TvShow
            else -> Type.Movie
        }

        DetailScreen(type, id)
    }
}
