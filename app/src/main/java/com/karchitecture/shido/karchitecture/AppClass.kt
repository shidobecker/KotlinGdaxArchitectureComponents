package com.karchitecture.shido.karchitecture

import android.app.Application
import android.arch.persistence.room.Room
import com.github.kittinunf.fuel.httpGet
import com.karchitecture.shido.karchitecture.datas.AppDatabase
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder
import com.karchitecture.shido.karchitecture.extensions.e
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Shido on 01/10/2017.
 */
val db by lazy { //Only set this value when somone uses this, and then evaluates this statement
    AppClass.tempTB!!
}

class AppClass: Application() {
    val endpoint = "https://api.gdax.com/products/BTC-USD/book?level=3"

    companion object {
        var tempTB: AppDatabase? = null
    }
    override fun onCreate() {
        tempTB = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
        downloadOrderBook()
        super.onCreate()
    }

    fun downloadOrderBook(){
        e("download order book")
        endpoint.httpGet().responseString { request, response, result ->
            result.fold({
                data -> val json = JSONObject(data)
                val sequence = json["sequence"] as Long
                val bids = json.getJSONArray("bids")
                val asks = json.getJSONArray("asks")

                addOpenOrders("buy",sequence, bids)
                addOpenOrders("sell",sequence, asks)

            }, {error -> e(error)})
        }
      /*  result.fold({ data ->
            val json = JSONObject(data)
            val sequence = json["sequence"] as Int
            val bids = json.getJSONArray("bids")
            val asks = json.getJSONArray("asks")

            addOpenOrders(sequence, bids)
            addOpenOrders(sequence, asks)


        },{ error -> e(error) })
        }*/
    }

    private fun addOpenOrders(side: String, sequence: Long, orders: JSONArray){
        val openOrders: MutableList<OpenOrder> = mutableListOf()
        (0..orders.length()-1).map {
            e(orders[it])
            val price = orders.getJSONArray(it)[0] as String
            val size = orders.getJSONArray(it)[1] as String
            val order_id = orders.getJSONArray(it)[2] as String

            val event = OpenOrder(sequence, "open", order_id, price.toFloat(), size, side )
            openOrders.add(event)
        }
        db.openOrderDao().insert(openOrders)
    }


}