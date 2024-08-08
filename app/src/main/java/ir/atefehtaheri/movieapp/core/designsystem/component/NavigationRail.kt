package ir.atefehtaheri.movieapp.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MovieNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MovieNavigationRailDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}



@Composable
fun MovieNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = MovieNavigationRailDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MovieNavigationRailDefaults.navigationContentColor(),
            selectedTextColor = MovieNavigationRailDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MovieNavigationRailDefaults.navigationContentColor(),
            indicatorColor = MovieNavigationRailDefaults.navigationIndicatorColor(),
        ),
    )
}


object MovieNavigationRailDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primary
}