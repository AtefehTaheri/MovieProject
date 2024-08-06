package ir.atefehtaheri.nowplaying.remote.models

data class NowPlayingDto(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)