package ir.atefehtaheri.movieapp.data.detailitem.remote.models.movie

data class MovieDetailDto(
    val adult: Boolean,
    val credits: Credits,
    val genres: List<Genre>,
    val id: Int,
    val images: Images,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
)