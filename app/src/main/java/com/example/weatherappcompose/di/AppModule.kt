package com.example.weatherappcompose.di

import com.example.weatherappcompose.datasource.networking.ApiService
import com.example.weatherappcompose.datasource.networking.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRetrofit():ApiService =
        Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
     fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    fun proivdesCoroutines() = CoroutinesDispatcherProvider()

    companion object {
        const val NETWORK_REQUEST_TIMEOUT_SECONDS = 15L
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}