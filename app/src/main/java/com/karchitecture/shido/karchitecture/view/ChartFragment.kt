package com.karchitecture.shido.karchitecture.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.httpGet
import com.karchitecture.shido.karchitecture.R
import com.karchitecture.shido.karchitecture.datas.model.Candle
import com.karchitecture.shido.karchitecture.extensions.e
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

/**
 * Created by Shido on 01/10/2017.
 */
class ChartFragment: Fragment() {

    val granularity = TimeUnit.MINUTES.toSeconds(15)
    val endpoint = "https://api.gdax.com/products/ETH-USD/candles?granularity=$granularity"

    var candlesList = listOf<Candle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    init {
        getCandles({},{})

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(candlesList.isEmpty()){
            return buildErrorLayout()
        }else{

            return buildCandlesLayout()
        }
    }


    fun buildErrorLayout(): View{
        return context.relativeLayout {
            textView{
                backgroundColor = ContextCompat.getColor(context, R.color.mainActivityBG)
                text = "Error trying to get candles"
                textColor = ContextCompat.getColor(context, R.color.red)

            }.lparams(height = wrapContent, width = matchParent){
                centerVertically()
                centerHorizontally()

            }
        }

    }

    fun buildCandlesLayout(): View{
        return context.relativeLayout{
            backgroundColor = ContextCompat.getColor(context, R.color.mainActivityBG)

            addView(Rectangle(context))

        }
    }


    fun getCandles(onSuccess: ()-> Unit, onError: () -> Unit){
        endpoint.httpGet().responseString { request, response, result ->
            result.fold({
                data ->
                println("Data $data")
                val cleanedData = data.removePrefix("[[").removeSuffix("]]") //Removendo os primeiros [[ e ]] do começo e fim
                //Removendo [ ] de cada linha e mapeando em um array
                candlesList = cleanedData.split("],[").map {
                    val segment = it.split(",")// removendo as virgulas e criando uma nova Candle a partir do resultado pegando cada segmento
                    Candle(segment[0].toLong(), segment[1].toFloat(),
                            segment[2].toFloat(), segment[3].toFloat(),
                            segment[4].toFloat(), segment[5].toDouble())

                }
                candlesList.forEach { println(it) }
                onSuccess()
            }, { error ->
                println("Error $error")
                onError()
            })
        }
    }


   inner class Rectangle (context: Context): View(context) {

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            e("ON DRAW")
            val candleWidth = canvas.width / (candlesList.size.toFloat())
            val highestPrice = checkNotNull(candlesList.maxBy { it.high }?.high)
            val lowestPrice = checkNotNull(candlesList.minBy { it.low }?.low)

            val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            greenPaint.color = ContextCompat.getColor(context, R.color.green)
            greenPaint.style = Paint.Style.FILL
            val redPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            redPaint.color = ContextCompat.getColor(context, R.color.red)

            candlesList.forEachIndexed{
                index, candle ->
                val left = canvas.width - candleWidth * (index * 1f)
                val right = canvas.width - candleWidth * (index * 1f)
                val bottom = canvas.height - (candle.low - lowestPrice) / (highestPrice - lowestPrice) * canvas.height
                val top = canvas.height - (candle.high - lowestPrice) / (highestPrice - lowestPrice) * canvas.height
                e("$left $right $bottom $top")
                canvas.drawRect(left, top, right, bottom, greenPaint)

            }
            e("Highest price ${highestPrice} lowest price $lowestPrice  $candleWidth")

        }
    }

}