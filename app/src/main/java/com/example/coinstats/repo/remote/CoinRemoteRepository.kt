package com.example.coinstats.repo.remote

import com.example.coinstats.repo.remote.api.CoinApi
import com.example.coinstats.repo.toDao

class CoinRemoteRepository(
    private val coinApi: CoinApi
) {
    suspend fun getCoins() =
        coinApi.getCoins(0, 100).toDao()

}