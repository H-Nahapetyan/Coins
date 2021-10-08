package com.example.coinstats.view.overview

import androidx.lifecycle.viewModelScope
import com.example.coinstats.repo.local.CoinLocalRepository
import com.example.coinstats.repo.local.dao.CoinDao
import com.example.coinstats.repo.remote.CoinRemoteRepository
import com.example.coinstats.utils.BaseViewModel
import com.example.coinstats.utils.state
import kotlinx.coroutines.async


class OverviewViewModel(
    private val coinRemoteRepo: CoinRemoteRepository,
    private val coinLocalRepo: CoinLocalRepository,
) : BaseViewModel() {

    var coinsList: List<CoinDao>? by state(emptyList())
    var favoriteCoins = ArrayList<CoinDao>()

    init {
        runAsync {
            val localCoinsList = viewModelScope.async { coinLocalRepo.getCoins() }
            val remoteCoinsList = viewModelScope.async { coinRemoteRepo.getCoins() }
//        val localCoinsList = runAsync { coinLocalRepo.getCoins() }
//        val remoteCoinsList = runAsync { coinRemoteRepo.getCoins() }

            if (coinsList.isNullOrEmpty()) {
                coinsList = localCoinsList.await()
            } else {
                coinsList = remoteCoinsList.await()
            }
            coinLocalRepo.storeCoins(remoteCoinsList.await())
        }
    }

    fun onAddFavorite(coin: CoinDao) {
        favoriteCoins.add(coin)
    }
}