package com.example.coinstats.retrofit

import com.example.coinstats.repo.remote.api.CachingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    companion object : KoinComponent {

        val cache: Cache by inject()
        val loggingInterceptor: HttpLoggingInterceptor by inject()
        val gson: GsonConverterFactory by inject()
        val cachingInterceptor: CachingInterceptor by inject()

        inline fun <reified T> create(): T {
            val okHttpClient = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .cache(cache)
                .addInterceptor(cachingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.coinstats.app/public/v1/")
                .client(okHttpClient)
                .addConverterFactory(gson)
                .build()

            return retrofit.create(T::class.java)
        }

    }
}