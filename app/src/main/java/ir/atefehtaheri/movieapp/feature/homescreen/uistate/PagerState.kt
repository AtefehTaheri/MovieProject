package ir.atefehtaheri.movieapp.feature.homescreen.uistate



data class PagerState<T>(
    val listDataModel: T? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)