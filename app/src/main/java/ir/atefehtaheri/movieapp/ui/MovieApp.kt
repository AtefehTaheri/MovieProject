package ir.atefehtaheri.movieapp.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.window.layout.DisplayFeature
import ir.atefehtaheri.movieapp.core.common.models.AppNavigationType
import ir.atefehtaheri.movieapp.navigation.MovieBottomBar
import ir.atefehtaheri.movieapp.navigation.MovieNavHost
import ir.atefehtaheri.movieapp.navigation.MovieNavRail

@Composable
fun MovieApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: MovieAppState = rememberMyAppState(
        windowSizeClass = windowSizeClass,
        displayFeatures = displayFeatures
    )
) {

//    when (appState.navigationType) {
//        AppNavigationType.BOTTOM_NAVIGATION, AppNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
//            MovieScaffold(
//                appState,
////                false,
//            )
//        }
//
//        AppNavigationType.NAVIGATION_RAIL -> {
//            MovieScaffold(
//                appState,
////                true,
//            )
//        }
//    }

    MovieScaffold(appState)
}


@Composable
private fun MovieScaffold(
    appState: MovieAppState,
//    shouldShowNavRail: Boolean,
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                MovieBottomBar(
                    navItems = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,

                    )
            }
        },
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (appState.shouldShowNavRail) {
                MovieNavRail(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.safeDrawingPadding(),
                )
            }

            Column(Modifier.fillMaxSize()) {
                MovieNavHost(
                    appState = appState
                )

            }
        }
    }
}





