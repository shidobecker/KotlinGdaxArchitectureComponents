package com.karchitecture.shido.karchitecture.extensions

import android.util.Log

/**
 * Created by Shido on 08/09/2017.
 */
fun Any.e(any: Any?="no message provided"){
    Log.e(this.javaClass.simpleName + " - " , any.toString())
}