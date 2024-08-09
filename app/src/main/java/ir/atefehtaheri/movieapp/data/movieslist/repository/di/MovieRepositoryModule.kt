package ir.atefehtaheri.movieapp.data.movieslist.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.movieslist.repository.MoviesRepository
import ir.atefehtaheri.movieapp.data.movieslist.repository.NetworkMoviesRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface MoviesRepositoryModule {
    @Singleton
    @Binds
    fun getMoviesRepositoryModule(
        networkMoviesRepository: NetworkMoviesRepository
    ): MoviesRepository
}