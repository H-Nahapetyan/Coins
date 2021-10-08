package com.example.coinstats.repo.remote.api

import com.example.coinstats.repo.remote.dto.GetCoinsResponse
import retrofit2.http.*

interface CoinApi {
    @GET("coins?skip=0&limit=5")
    suspend fun getCoins( @Query("skip") skip : Int, @Query("limit") limit : Int ): GetCoinsResponse

}