package ir.atefehtaheri.weatherforecasts.data.currentweather.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.topratedmovie.remote.TopRatedMovieDatasource
import ir.atefehtaheri.topratedmovie.remote.OnlineTopRatedMovieDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TopRatedMovieDataSourceModule {

    @Singleton
    @Binds
    fun getTopRatedMovieDatasourceModule(
        onlineTopRatedMovieDatasource: OnlineTopRatedMovieDatasource
    ): TopRatedMovieDatasource

}