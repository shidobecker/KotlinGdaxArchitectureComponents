package com.karchitecture.shido.karchitecture

import com.google.gson.GsonBuilder
import com.karchitecture.shido.karchitecture.datas.model.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * Created by mira on 11/09/2017.
 */
class MessageParser {
    val gson = GsonBuilder().serializeNulls().create()

    fun readMessage(message: String) {
        val JSON = JSONObject(message)
        val type = JSON["type"].toString()
        async(CommonPool){
         when (type) {
            "open" -> {
                buildAndInsertOpenOrder(message)
            }
            "change" -> {
                buildAndInsertChangeOrder(message)
            }
            "received" -> {
                buildAndInsertReceivedOrder(message)
            }
            "match" -> {
                buildAndInsertMatchOrder(message)
            }
            "done" -> {
                buildAndInsertDoneOrder(message)
            }
            else -> null
        }
        }

    }

    fun buildAndInsertOpenOrder(message: String): OpenOrder {
        val openOrder = gson.fromJson(message, OpenOrder::class.java)
        db.openOrderDao().insert(openOrder)

        return openOrder
    }

    fun buildAndInsertReceivedOrder(text: String): ReceivedOrder {
        val receivedOrder = gson.fromJson(text, ReceivedOrder::class.java)
            db.receivedOrderDao().insert(receivedOrder)

        return receivedOrder
    }

    fun buildAndInsertMatchOrder(message: String): MatchOrder {
        val matchOrder = gson.fromJson(message, MatchOrder::class.java)
        db.matchOrderDao().insert(matchOrder)

        return matchOrder
    }

    fun buildAndInsertDoneOrder(message: String): DoneOrder {
        val doneOrder = gson.fromJson(message, DoneOrder::class.java)
            db.doneOrderDao().insert(doneOrder)

        return doneOrder
    }

    fun buildAndInsertChangeOrder(message: String): ChangeOrder {
        val changeOrder = gson.fromJson(message, ChangeOrder::class.java)
        db.changeOrderDao().insert(changeOrder)

        return changeOrder
    }

}