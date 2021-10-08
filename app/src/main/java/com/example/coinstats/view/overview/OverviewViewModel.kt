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

    var coinsList: ArrayList<CoinDao> by state(ArrayList())
    var favoriteCoins = ArrayList<CoinDao>()

    init {
        runAsync {
            val localCoinsList = viewModelScope.async { coinLocalRepo.getCoins() }
            if (coinsList.isNullOrEmpty()) {
                coinsList.addAll(localCoinsList.await())
                notifyDataChanged()
            }

            val remoteCoinsList = viewModelScope.async { coinRemoteRepo.getCoins() }
            val remoteList = remoteCoinsList.await()
            coinsList = ArrayList()
            coinsList.addAll(remoteList)
            coinLocalRepo.storeCoins(remoteCoinsList.await())
        }
    }

    fun onAddFavorite(coin: CoinDao) {
        favoriteCoins.add(coin)
    }
}