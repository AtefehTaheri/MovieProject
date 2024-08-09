package ir.atefehtaheri.movieapp.data.tvshowlist.remote.models

data class TvShowsDto(
    val page: Int,
    val results: List<TvShow>,
    val total_pages: Int,
    val total_results: Int
)