package ir.atefehtaheri.movieapp.feature.upcominglistScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import ir.atefehtaheri.movieapp.core.common.models.AppContentType
import ir.atefehtaheri.movieapp.core.common.models.MediaType
import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.feature.listscreen.MovieListRoute

const val MovieListRoute =
    "movielist_route?mediaTypeTvshow={mediaTypeTvshow}&mediaTypeMovie={mediaTypeMovie}"

fun NavController.navigateToMovieList(
    mediaTypeMovie: MediaType.Movie,
    mediaTypeTvshow: MediaType.TvShow,
    navOptions: NavOptions? = null
) {
    this.navigate(
        MovieListRoute
            .replace("{mediaTypeTvshow}", mediaTypeTvshow.mediaType)
            .replace("{mediaTypeMovie}", mediaTypeMovie.mediaType), navOptions
    )
}

fun NavGraphBuilder.movieListDestination(
    onItemClick: (Type, Int, NavOptions?) -> Unit,
    displayFeatures: List<DisplayFeature>,
    contentType: AppContentType
) {
    composable(route = MovieListRoute,
        arguments = listOf(
            navArgument("mediaTypeTvshow") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("mediaTypeMovie") {
                type = NavType.StringType
                defaultValue = ""
            }
        )

    ) {
        val typeTvshowArgument = it.arguments!!.getString("mediaTypeTvshow")!!

        val mediaTypeTvshow = when (typeTvshowArgument) {
            MediaType.TvShow.Airing.mediaType -> MediaType.TvShow.Airing
            else -> MediaType.TvShow.TOP_RATED
        }
        val typeMovieArgument = it.arguments!!.getString("mediaTypeMovie")!!
        val mediaTypeMovie = when (typeMovieArgument) {
            MediaType.Movie.UPCOMING.mediaType -> MediaType.Movie.UPCOMING
            MediaType.Movie.TOP_RATED.mediaType -> MediaType.Movie.TOP_RATED
            else -> MediaType.Movie.NOW_PLAYING
        }

        MovieListRoute(
            onItemClick = onItemClick,
            mediaTypeMovie = mediaTypeMovie,
            mediaTypeTvShow = mediaTypeTvshow,
            displayFeatures=displayFeatures,
            contentType=contentType
        )

    }
}
