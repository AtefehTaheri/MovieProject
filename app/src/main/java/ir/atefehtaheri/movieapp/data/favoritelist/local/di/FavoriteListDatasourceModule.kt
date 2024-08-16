package ir.atefehtaheri.movieapp.data.favoritelist.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.favoritelist.local.FavoriteListDatasource
import ir.atefehtaheri.movieapp.data.favoritelist.local.LocalFavoriteListDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FavoriteListDatasourceModule {

    @Singleton
    @Binds
    fun getFavoriteListDatasourceModule(
        localFavoriteListDataSource: LocalFavoriteListDataSource
    ): FavoriteListDatasource
}