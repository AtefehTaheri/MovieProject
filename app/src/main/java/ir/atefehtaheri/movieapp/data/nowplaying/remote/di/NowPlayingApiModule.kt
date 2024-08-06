package ir.atefehtaheri.weatherforecasts.data.currentweather.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.core.network.di.createApiService
import ir.atefehtaheri.movieapp.data.nowplaying.remote.api.NowPlayingApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NowPlayingApiModule {

    @Provides
    @Singleton
    fun getNowPlayingApiModule(retrofit: Retrofit): NowPlayingApi {
        return createApiService(NowPlayingApi::class.java, retrofit)
    }

}