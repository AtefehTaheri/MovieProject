package ir.atefehtaheri.weatherforecasts.data.currentweather.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.upcominglist.remote.OnlineUpcomingListDatasource
import ir.atefehtaheri.upcominglist.remote.UpcomingListDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpcomingListDataSourceModule {

    @Singleton
    @Binds
    fun getUpcomingListDataSourceModule(
        onlineUpcomingListDatasource: OnlineUpcomingListDatasource
    ): UpcomingListDatasource
}