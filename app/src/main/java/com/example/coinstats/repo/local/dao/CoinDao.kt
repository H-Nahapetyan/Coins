package com.example.coinstats.repo.local.dao
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CoinDao : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var price: Double? = null
}