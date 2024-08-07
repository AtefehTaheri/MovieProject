package ir.atefehtaheri.movieapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import ir.atefehtaheri.movieapp.R
import ir.atefehtaheri.movieapp.core.designsystem.icon.MovieIcons


enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = MovieIcons.Home,
        unselectedIcon = MovieIcons.HomeBorder,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
    ),
    SEARCH(
        selectedIcon = MovieIcons.Search,
        unselectedIcon = MovieIcons.SearchBorder,
        iconTextId = R.string.search,
        titleTextId = R.string.search,
    ),
    FAVORITE(
        selectedIcon = MovieIcons.Favorite,
        unselectedIcon = MovieIcons.FavoriteBorder,
        iconTextId = R.string.favorite,
        titleTextId = R.string.favorite,
    )
}
