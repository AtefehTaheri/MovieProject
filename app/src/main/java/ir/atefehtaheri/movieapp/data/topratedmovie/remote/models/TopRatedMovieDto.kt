package ir.atefehtaheri.topratedmovie.remote.models

data class TopRatedMovieDto(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)