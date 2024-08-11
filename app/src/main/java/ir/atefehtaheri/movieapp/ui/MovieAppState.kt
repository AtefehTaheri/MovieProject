package com.omidtaheri.template.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import ir.atefehtaheri.movieapp.core.common.models.AppContentType
import ir.atefehtaheri.movieapp.core.common.models.AppNavigationType
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.HomeScreenRoute
import ir.atefehtaheri.movieapp.feature.homescreen.navigation.navigateToHomeScreen
import ir.atefehtaheri.movieapp.navigation.TopLevelDestination


@Composable
fun rememberMyAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),

    ): MovieAppState {
    return remember(
        navController,
        windowSizeClass,
    ) {
        MovieAppState(
            navController,
            windowSizeClass
        )
    }
}

@Stable
class MovieAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
) {

    val navigationType: AppNavigationType
    val contentType: AppContentType


    init {

        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                navigationType = AppNavigationType.BOTTOM_NAVIGATION
                contentType = AppContentType.SINGLE_PANE
            }

            WindowWidthSizeClass.Medium -> {
                navigationType = AppNavigationType.NAVIGATION_RAIL
                contentType = AppContentType.SINGLE_PANE

            }

            WindowWidthSizeClass.Expanded -> {
                navigationType = AppNavigationType.PERMANENT_NAVIGATION_DRAWER
                contentType = AppContentType.DUAL_PANE
            }

            else -> {
                navigationType = AppNavigationType.BOTTOM_NAVIGATION
                contentType = AppContentType.SINGLE_PANE
            }
        }
    }


    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HomeScreenRoute -> TopLevelDestination.HOME
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> {
                navController.navigateToHomeScreen()
            }
            TopLevelDestination.SEARCH -> {
//                navController.navigateToSearchScreen()
            }
            TopLevelDestination.FAVORITE -> {}
        }
    }
}
