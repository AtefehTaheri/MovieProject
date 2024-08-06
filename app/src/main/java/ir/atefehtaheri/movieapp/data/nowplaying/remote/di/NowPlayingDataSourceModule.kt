package ir.atefehtaheri.weatherforecasts.data.currentweather.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.nowplaying.remote.NowPlayingDatasource
import ir.atefehtaheri.nowplaying.remote.OnlineNowPlayingDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NowPlayingDataSourceModule {

    @Singleton
    @Binds
    fun getNowPlayingDataSourceModule(
        nowPlayingDatasourceImpl: OnlineNowPlayingDatasource
    ): NowPlayingDatasource
}