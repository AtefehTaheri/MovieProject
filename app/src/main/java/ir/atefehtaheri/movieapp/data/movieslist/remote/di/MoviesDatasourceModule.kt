package ir.atefehtaheri.movieapp.data.movieslist.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.movieslist.remote.MoviesDatasource
import ir.atefehtaheri.movieapp.data.movieslist.remote.NetworkMoviesDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MoviesDatasourceModule {

    @Singleton
    @Binds
    fun getMoviesDatasourceModule(
        networkMoviesDatasource: NetworkMoviesDatasource
    ): MoviesDatasource
}