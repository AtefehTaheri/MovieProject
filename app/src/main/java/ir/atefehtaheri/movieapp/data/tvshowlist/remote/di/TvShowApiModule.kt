package ir.atefehtaheri.movieapp.data.tvshowlist.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.core.network.di.createApiService
import ir.atefehtaheri.movieapp.data.tvshowlist.remote.api.TvShowApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvShowApiModule {

    @Provides
    @Singleton
    fun getTvShowApiModule(retrofit: Retrofit): TvShowApi {
        return createApiService(TvShowApi::class.java, retrofit)
    }

}