package ir.atefehtaheri.upcominglist.remote.models

data class UpcomingListDto(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)