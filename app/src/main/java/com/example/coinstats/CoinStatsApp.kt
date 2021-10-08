package com.example.coinstats

import android.app.Application
import com.example.coinstats.di.appModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoinStatsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoinStatsApp)
            modules(appModule)
        }
        Realm.init(this)
    }
}