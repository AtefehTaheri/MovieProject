package ir.atefehtaheri.movieapp.data.detailitem.repository.models

import ir.atefehtaheri.movieapp.core.common.models.Type
import ir.atefehtaheri.movieapp.data.detailitem.remote.models.tvshow.TvShowDetailDto
import ir.atefehtaheri.movieapp.data.favoritelist.local.models.FavoriteMovie


data class TvShowDetailDataModel(
    val adult: Boolean,
    val credits: Credits,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<String>,
    val id: Int,
    val images: List<String>,
    val last_air_date: String?,
    val last_episode_to_air: Episode,
    val name: String,
    val next_episode_to_air: Episode?,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val poster_path: String?,
    val seasons: List<Season>,
    val status: String,
    val vote_average: Double,
    val isFavorite: Boolean

)

fun TvShowDetailDto.asTvShowDetailDataModel(isFavorite: Boolean):TvShowDetailDataModel{
    return TvShowDetailDataModel(
        genres=genres.map{it.name},
        adult=adult,
        poster_path=poster_path,
        name = name,
        id=id,
        first_air_date=first_air_date,
        vote_average=vote_average,
        status=status,
        last_air_date=last_air_date,
        overview=overview,
        credits=Credits(
            credits.cast.map { Cast(it.name,it.profile_path) }
            ,credits.crew.map {Crew(it.job,it.name,it.profile_path)
            }),
        images=images.backdrops.map { it.file_path },
        number_of_episodes=number_of_episodes,
        number_of_seasons =number_of_seasons,
        episode_run_time =episode_run_time,
        last_episode_to_air =last_episode_to_air.run {
            Episode(episode_number,name,runtime,season_number,still_path,vote_average)
        }
                ,
        next_episode_to_air=next_episode_to_air?.let {
            Episode(it.episode_number,it.name,it.runtime,it.season_number,it.still_path,it.vote_average)
        },
        seasons=seasons.map { it.run{
            Season(
                episode_count,
                name,
                poster_path
                ,season_number
                ,vote_average)}
        },
        isFavorite=isFavorite
    )
}

fun TvShowDetailDataModel.asFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        title = name,
        overview = overview,
        poster_path = poster_path,
        release_date = first_air_date,
        vote_average = vote_average,
        type_movie = Type.TvShow,
    )

}