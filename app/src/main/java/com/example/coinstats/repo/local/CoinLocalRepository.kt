package com.example.coinstats.repo.local

import com.example.coinstats.repo.local.dao.CoinDao
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class CoinLocalRepository {
    private var  config : RealmConfiguration = RealmConfiguration.Builder()
        .schemaVersion(1L)
        .build()

    suspend fun storeCoins(coins: List<CoinDao>) {

        val realm = Realm.getInstance(config)

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.delete(CoinDao::class.java)

        }
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (coin in coins) {
                realmTransaction.insert(coin)
            }
        }
    }

    fun getCoins(): List<CoinDao> {
        val realm = Realm.getInstance(config)
        return realm.where(CoinDao::class.java).findAll()
    }

}