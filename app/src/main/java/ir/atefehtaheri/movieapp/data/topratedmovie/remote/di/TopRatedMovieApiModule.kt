package ir.atefehtaheri.weatherforecasts.data.currentweather.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.core.network.di.createApiService
import ir.atefehtaheri.movieapp.data.topratedmovie.remote.api.TopRatedMovieApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopRatedMovieApiModule {

    @Provides
    @Singleton
    fun getTopRatedMovieApiModule(retrofit: Retrofit): TopRatedMovieApi {
        return createApiService(TopRatedMovieApi::class.java, retrofit)
    }

}