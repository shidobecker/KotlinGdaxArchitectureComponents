package com.karchitecture.shido.karchitecture

import android.app.Application
import android.arch.persistence.room.Room
import com.github.kittinunf.fuel.httpGet
import com.karchitecture.shido.karchitecture.datas.AppDatabase
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder
import com.karchitecture.shido.karchitecture.extensions.e
import org.json.JSONObject

/**
 * Created by Shido on 01/10/2017.
 */
val db by lazy { //Only set this value when somone uses this, and then evaluates this statement
    AppClass.db!!
}

class AppClass: Application() {
    val endpoint = "https://api.gdax.com/products/BTC-USD/book?level=2"

    companion object {
        var db: AppDatabase? = null
    }
    override fun onCreate() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
        super.onCreate()
    }

    fun downloadOrderBook(){
        val (request, response, result) = endpoint.httpGet().responseString()
        // e("$request $result $response")
        result.fold({ data ->
            val json = JSONObject(data)
            val sequence = json["sequence"] as Int
            val bids = json.getJSONArray("bids")
            val asks = json.getJSONArray("asks")
            (0..bids.length()-1).map{
               // val event = OpenOrder(sequence, "open", "", )
                e(bids[it])
            }
            e(sequence)
            e(bids)
            e(asks)
            (0..asks.length()).map {
                e(asks[it])
            }
        },{ error ->
            e(error)
        })
    }
}