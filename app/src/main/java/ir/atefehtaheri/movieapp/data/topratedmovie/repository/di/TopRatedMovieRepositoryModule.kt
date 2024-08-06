package ir.atefehtaheri.movieapp.data.topratedmovie.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.topratedmovie.repository.OnlineTopRatedMovieRepository
import ir.atefehtaheri.topratedmovie.repository.TopRatedMovieRepository
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
interface TopRatedMovieRepositoryModule {
    @Singleton
    @Binds
    fun getTopRatedMovieRepositoryModule(
        onlineTopRatedMovieRepository: OnlineTopRatedMovieRepository
    ): TopRatedMovieRepository
}