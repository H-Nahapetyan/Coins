package com.example.coinstats.repo.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class CachingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        newRequest.addHeader("Cache-Control", "no-cache, no-store, must-revalidate")
        return chain.proceed(newRequest.build())
    }
}