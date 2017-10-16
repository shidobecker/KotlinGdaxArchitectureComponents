package com.karchitecture.shido.karchitecture.extensions

import android.graphics.Color
import android.util.Log
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * Created by Shido on 08/09/2017.
 */

val maxTradeSize = 1000f

val green = Color.parseColor("#70ce5c")
val red = Color.parseColor("#ff6939")
val primaryColor = Color.parseColor("#1e2b34")
val primaryColorLight = Color.parseColor("#2F3D45")

fun Any.e(any: Any? = "no message provided") {
    Log.e(this.javaClass.simpleName + " - ", any.toString())
}

fun formatNumString(number: Float, decimalSpots: Int): String {
    val beforeDec = number.toString().substringBefore(".")
    val afterDec = number.toString().substringAfter(".").padEnd(decimalSpots, '0')
    return "$beforeDec.$afterDec"
}

fun formatNumString2(number: Float): String {
    val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    df.maximumFractionDigits = 340
    return df.format(number)
}
