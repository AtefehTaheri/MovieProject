package ir.atefehtaheri.movieapp.data.nowplaying.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.nowplaying.repository.NowPlayingRepository
import ir.atefehtaheri.nowplaying.repository.OnlineNowPlayingRepository
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
interface NowPlayingRepositoryModule {
    @Singleton
    @Binds
    fun getNowPlayingRepositoryModule(
        onlineNowPlayingRepository: OnlineNowPlayingRepository
    ): NowPlayingRepository
}