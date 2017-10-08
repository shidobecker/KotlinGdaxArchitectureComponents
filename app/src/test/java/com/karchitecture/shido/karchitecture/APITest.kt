package com.karchitecture.shido.karchitecture

import com.github.kittinunf.fuel.httpGet
import org.json.JSONObject
import org.junit.Test

/**
 * Created by Shido on 01/10/2017.
 */
class APITest {
    val endpoint = "https://api.gdax.com/products/BTC-USD/book?level=2"

    @Test
    fun runTest(){
        val (request, response, result) = endpoint.httpGet().responseString()
        // e("$request $result $response")
        result.fold({ data ->
            val json = JSONObject(data)
            val sequence = json["sequence"]
            val bids = json.getJSONArray("bids")
            val asks = json.getJSONArray("asks")
           // println(sequence)
           // println(bids)
           // println(asks)
            (0..asks.length()).map {
                println(asks.getJSONArray(it)[0])
               // println(asks[it])
            }
        },{ error ->
            println(error)
        })
    }


}