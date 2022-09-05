package com.krasovitova.currencywallet.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.krasovitova.currencywallet.api.CurrencyApi
import com.krasovitova.currencywallet.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
    }

    @Provides
    fun provideCurrencyApi(retrofitBuilder: Retrofit.Builder): CurrencyApi {
        return retrofitBuilder
            .baseUrl("https://api.exchangerate.host/")
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    fun provideImageApi(retrofitBuilder: Retrofit.Builder): ImageApi {
        return retrofitBuilder
            .baseUrl("https://www.flickr.com/services/rest/")
            .build()
            .create(ImageApi::class.java)
    }
}