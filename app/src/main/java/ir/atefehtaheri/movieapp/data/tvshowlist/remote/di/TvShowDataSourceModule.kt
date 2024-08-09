package ir.atefehtaheri.movieapp.data.tvshowlist.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.NetworkTvShowsDatasource
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.TvShowsDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TvShowDataSourceModule {

    @Singleton
    @Binds
    fun getTvShowDataSourceModule(
        networkTvShowsDatasource: NetworkTvShowsDatasource
    ): TvShowsDatasource

}