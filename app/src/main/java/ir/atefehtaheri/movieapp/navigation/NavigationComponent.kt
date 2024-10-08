package ir.atefehtaheri.movieapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ir.atefehtaheri.movieapp.core.designsystem.component.MovieNavigationBar
import ir.atefehtaheri.movieapp.core.designsystem.component.MovieNavigationBarItem
import ir.atefehtaheri.movieapp.core.designsystem.component.MovieNavigationRail
import ir.atefehtaheri.movieapp.core.designsystem.component.MovieNavigationRailItem


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {

    return this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false
}

@Composable
fun MovieBottomBar(
    navItems: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MovieNavigationBar(
        modifier = modifier,
    ) {
        navItems.forEach { destination ->

            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MovieNavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentDestination?.route != destination.route) {
                        onNavigateToDestination(destination)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = stringResource(id = destination.titleTextId),
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = stringResource(id = destination.titleTextId),
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier
            )
        }
    }
}


@Composable
fun MovieNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MovieNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MovieNavigationRailItem(
                selected = selected,
                onClick = {
                    if (currentDestination?.route != destination.route) {
                        onNavigateToDestination(destination)
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}


