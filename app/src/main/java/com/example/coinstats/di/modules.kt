package com.example.coinstats.di

import com.example.coinstats.BuildConfig
import com.example.coinstats.repo.local.CoinLocalRepository
import com.example.coinstats.repo.remote.CoinRemoteRepository
import com.example.coinstats.repo.remote.api.CoinApi
import com.example.coinstats.repo.remote.api.CachingInterceptor
import com.example.coinstats.retrofit.ServiceGenerator
import com.example.coinstats.utils.BaseViewModel
import com.example.coinstats.view.overview.OverviewViewModel
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

private const val realmVersion = 1L


val appModule = module {
    val cacheSize = Runtime.getRuntime().maxMemory() / 8

    viewModel { BaseViewModel() }
    viewModel { OverviewViewModel(get(), get()) }

    single { CoinRemoteRepository(get()) }
    single { CoinLocalRepository() }

    single { GsonConverterFactory.create() }
    single { ServiceGenerator.create<CoinApi>() }
    single {
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        )
    }
    factory { CachingInterceptor() }
    single { Cache(androidContext().cacheDir, cacheSize) }
}
