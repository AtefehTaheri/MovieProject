package ir.atefehtaheri.movieapp.data.movieslist.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.movieslist.remote.api.MovieApi
import ir.atefehtaheri.movieapp.core.network.di.createApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieApiModule {

    @Provides
    @Singleton
    fun getMovieApiModule(retrofit: Retrofit): MovieApi {
        return createApiService(MovieApi::class.java, retrofit)
    }

}