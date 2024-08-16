package ir.atefehtaheri.movieapp.data.detailitem.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.core.network.di.createApiService
import ir.atefehtaheri.movieapp.data.detailitem.remote.api.DetailItemApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailItemApiModule {

    @Provides
    @Singleton
    fun getDetailItemApiModule(retrofit: Retrofit): DetailItemApi {
        return createApiService(DetailItemApi::class.java, retrofit)
    }

    @Singleton
    @Provides
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
