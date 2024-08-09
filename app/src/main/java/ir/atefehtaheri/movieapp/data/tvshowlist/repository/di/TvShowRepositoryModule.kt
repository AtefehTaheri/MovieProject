package ir.atefehtaheri.movieapp.data.tvshowlist.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.NetworkTvShowsRepository
import ir.atefehtaheri.movieapp.data.tvshowlist.repository.TvShowsRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface TvShowRepositoryModule {
    @Singleton
    @Binds
    fun getTvShowRepositoryModule(
        networkTvShowsRepository: NetworkTvShowsRepository
    ): TvShowsRepository
}