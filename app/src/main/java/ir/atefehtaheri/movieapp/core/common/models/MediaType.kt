package ir.atefehtaheri.movieapp.core.common.models

sealed interface MediaType {

    enum class Movie(val mediaType: String) : MediaType {
        UPCOMING(UPCOMING_MEDIA),
        TOP_RATED(TOP_RATED_MEDIA),
        NOW_PLAYING(NOW_PLAYING_MEDIA)
    }

    enum class TvShow(val mediaType: String) : MediaType {
        TOP_RATED(TOP_RATED_MEDIA),
        Airing(Airing_MEDIA)
    }


}

private const val UPCOMING_MEDIA = "upcoming"
private const val TOP_RATED_MEDIA = "top_rated"
private const val NOW_PLAYING_MEDIA = "now_playing"
private const val Airing_MEDIA = "airing"