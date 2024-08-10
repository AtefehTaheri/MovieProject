package ir.atefehtaheri.movieapp.data.detailitem.repository.models

data class Episode(

    val episode_number: Int,
    val name: String,
    val runtime: Int,
    val season_number: Int,
    val still_path: String?,
    val vote_average: Double,

)
