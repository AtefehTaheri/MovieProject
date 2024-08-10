package ir.atefehtaheri.movieapp.core.network.di



import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.atefehtaheri.movieapp.BuildConfig
import ir.atefehtaheri.movieapp.core.network.Constants
import ir.atefehtaheri.network.adapter.NetworkResponseCallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.CallAdapter
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton

    fun provideRetrofitMovie(
        networkResponseCallAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return provideRetrofit(
            Constants.API_BASE_URL,
            networkResponseCallAdapterFactory
        )
    }

    @Singleton
    @Provides
    fun provideNetworkResponseCallAdapterFactory(): CallAdapter.Factory {
        return NetworkResponseCallAdapterFactory.create()
    }


}
fun provideRetrofit(
    baseUrl: String,
    networkResponseCallAdapterFactory: CallAdapter.Factory
): Retrofit {
     val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val apiKey = BuildConfig.API_KEY
    val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .addInterceptor(loggingInterceptor)
        .build()

    return Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(networkResponseCallAdapterFactory)
        .baseUrl(baseUrl)
        .build()
}
inline fun <reified T> createApiService(service: Class<T>, retrofit: Retrofit): T =
    retrofit.create(service)



class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val urlWithApiKey: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
//            .addQueryParameter("language","en-US")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}