package ir.atefehtaheri.movieapp.data.detailitem.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.detailitem.remote.DetailItemDatasource
import ir.atefehtaheri.movieapp.data.detailitem.remote.NetworkDetailItemDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DetailItemDataSourceModule {

    @Singleton
    @Binds
    fun getDetailItemDataSourceModule(
        networkDetailItemDatasource: NetworkDetailItemDatasource
    ): DetailItemDatasource

}