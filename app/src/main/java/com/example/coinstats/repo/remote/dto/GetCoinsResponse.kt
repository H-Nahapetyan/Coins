package com.example.coinstats.repo.remote.dto

import com.google.gson.annotations.SerializedName

class GetCoinsResponse (val coins: List<Coin>)

data class Coin (
    val id: String,
    val icon: String,
    val name: String,
    val symbol: String,
    val rank: Double,
    val price: Double,
    val priceBtc: Double,
    val volume: Double,
    val marketCap: Double,
    val availableSupply: Double,
    val totalSupply: Double,

    @SerializedName("priceChange1h")
    val priceChange1H: Double,

    @SerializedName("priceChange1d")
    val priceChange1D: Double,

    @SerializedName("priceChange1w")
    val priceChange1W: Double,

    @SerializedName("websiteUrl")
    val websiteURL: String,

    @SerializedName("redditUrl")
    val redditURL: String? = null,

    @SerializedName("twitterUrl")
    val twitterURL: String? = null,

    val exp: List<String>
)