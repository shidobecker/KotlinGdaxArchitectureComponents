package com.karchitecture.shido.karchitecture

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.karchitecture.shido.karchitecture.datas.model.Candle
import org.json.JSONObject
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by Shido on 21/10/2017.
 */
class ChartDataTest {
    val granularity = TimeUnit.MINUTES.toSeconds(15)
    val endpoint =  "https://api.gdax.com/products/ETH-USD/candles?granularity=$granularity"

    @Test
    fun runTest(){
        val (request, response, result) = endpoint.httpGet().responseString()
           result.fold({
               data -> println("Data $data")
               val cleanedData = data.removePrefix("[[").removeSuffix("]]") //Removendo os primeiros [[ e ]] do começo e fim
               //Removendo [ ] de cada linha e mapeando em um array
               val candlesList = cleanedData.split("],[").map {
                   val segment = it.split(",")// removendo as virgulas e criando uma nova Candle a partir do resultado pegando cada segmento
                   Candle(segment[0].toLong(), segment[1].toFloat(),
                           segment[2].toFloat(), segment[3].toFloat(),
                           segment[4].toFloat(), segment[5].toDouble())

               }
               candlesList.forEach { println(it) }

           }, { error -> println("Error $error")
           })
        }


}