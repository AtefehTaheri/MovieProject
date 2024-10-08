package ir.atefehtaheri.movieapp.data.movieslist.remote.models

data class MoviesDto(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)