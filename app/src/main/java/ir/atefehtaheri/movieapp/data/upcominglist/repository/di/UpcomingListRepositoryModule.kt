package ir.atefehtaheri.movieapp.data.upcominglist.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.upcominglist.repository.OnlineUpcomingListRepository
import ir.atefehtaheri.upcominglist.repository.UpcomingListRepository
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
interface UpcomingListRepositoryModule {

    @Singleton
    @Binds
    fun getUpcomingListRepositoryModule(
        upcomingListRepositoryImpl: OnlineUpcomingListRepository
    ): UpcomingListRepository
}