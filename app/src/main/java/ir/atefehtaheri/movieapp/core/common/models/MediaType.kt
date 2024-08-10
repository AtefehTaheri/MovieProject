package ir.atefehtaheri.movieapp.core.common.models

sealed interface MediaType {
    enum class Movie(val mediaType: String) : MediaType {
        UPCOMING(UPCOMING_MEDIA),
        TOP_RATED(TOP_RATED_MOVIE_MEDIA),
        NOW_PLAYING(NOW_PLAYING_MEDIA);

        companion object {
            val name = "Movie"
        }
    }

    enum class TvShow(val mediaType: String) : MediaType {
        TOP_RATED(TOP_RATED_TVSHOW_MEDIA),
        Airing(Airing_MEDIA);

        companion object {
            val name = "TvShow"
        }
    }
}

private const val UPCOMING_MEDIA = "upcoming"
private const val TOP_RATED_MOVIE_MEDIA = "top_rated_movie"
private const val TOP_RATED_TVSHOW_MEDIA = "top_rated_tvshow"
private const val NOW_PLAYING_MEDIA = "now_playing"
private const val Airing_MEDIA = "airing"