package com.example.coinstats.repo

import com.example.coinstats.repo.local.dao.CoinDao
import com.example.coinstats.repo.remote.dto.Coin
import com.example.coinstats.repo.remote.dto.GetCoinsResponse


internal fun GetCoinsResponse.toDao() : List<CoinDao> {
    return coins.map { it.toDao() }
}

internal fun Coin.toDao() : CoinDao {
    val coinDao = CoinDao()
        coinDao.id = id
        coinDao.name = name
        coinDao.price = price

    return coinDao
}

