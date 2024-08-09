package ir.atefehtaheri.movieapp.data.detailitem.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.data.detailitem.repository.DetailItemRepository
import ir.atefehtaheri.movieapp.data.detailitem.repository.NetworkDetailItemRepository
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
interface DetailItemRepositoryModule {
    @Singleton
    @Binds
    fun getDetailItemRepositoryModule(
        networkDetailItemRepository: NetworkDetailItemRepository
    ): DetailItemRepository
}