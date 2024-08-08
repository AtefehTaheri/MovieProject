package ir.atefehtaheri.movieapp.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MovieNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MovieNavigationBarDefaults.navigationContentColor(),
        tonalElevation = 10.dp,
        content = content,
    )
}

@Composable
fun RowScope.MovieNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MovieNavigationBarDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MovieNavigationBarDefaults.navigationContentColor(),
            selectedTextColor = MovieNavigationBarDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MovieNavigationBarDefaults.navigationContentColor(),
            indicatorColor = MovieNavigationBarDefaults.navigationIndicatorColor(),
        ),
    )
}


object MovieNavigationBarDefaults {

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.secondary
}

