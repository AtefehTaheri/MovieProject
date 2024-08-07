package ir.atefehtaheri.movieapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.omidtaheri.template.ui.MovieAppState
import com.omidtaheri.template.ui.rememberMyAppState
import ir.atefehtaheri.movieapp.core.common.models.AppNavigationType
import ir.atefehtaheri.movieapp.navigation.MovieNavHost

@Composable
fun MovieApp (
    windowSizeClass: WindowSizeClass,
    appState: MovieAppState = rememberMyAppState(
        windowSizeClass = windowSizeClass
    )
){

    when (appState.navigationType) {
        AppNavigationType.BOTTOM_NAVIGATION -> {
        }
        AppNavigationType.NAVIGATION_RAIL -> {
        }
        AppNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(drawerContent = {

            }) {
                MyAppContent(
                    appState=appState,
                    navigationType = appState.navigationType,
                )
            }

        }
    }




}



@Composable
fun MyAppContent(
    appState: MovieAppState,
    modifier: Modifier = Modifier,
    navigationType: AppNavigationType,
){
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == AppNavigationType.NAVIGATION_RAIL) {

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            MovieNavHost(
                appState = appState
            )
            AnimatedVisibility(visible = navigationType == AppNavigationType.BOTTOM_NAVIGATION) {
            }

        }
    }

}
