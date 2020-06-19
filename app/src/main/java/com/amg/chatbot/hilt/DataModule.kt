package com.amg.chatbot.hilt

import com.amg.chatbot.retrofit.DuckApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Provides
    fun provideInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideClient(interceptor: Interceptor): OkHttpClient {
        // Server is to slow. So, I give it too much time
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient, gsonConverter: GsonConverterFactory): DuckApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.duckduckgo.com/")
            .addConverterFactory(gsonConverter)
            .client(client)
            .build()
            .create(DuckApiService::class.java)
    }
}