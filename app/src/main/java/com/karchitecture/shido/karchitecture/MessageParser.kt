package com.karchitecture.shido.karchitecture

import com.karchitecture.shido.karchitecture.datas.model.*
import org.json.JSONObject

/**
 * Created by mira on 11/09/2017.
 */
class MessageParser(val db: AppDatabase) {

    fun readMessage(message: String) {
        val JSON = JSONObject(message)
        val type = JSON["type"].toString()
        val resultOrder = when (type) {
            "open" -> {
                buildAndInsertOpenOrder(JSON)
            }
            "change" -> {
                buildAndInsertChangeOrder(JSON)
            }
            "received" -> {
                buildAndInsertReceivedOrder(JSON)
            }
            "match" -> {
                buildAndInsertMatchOrder(JSON)
            }
            "done" -> {
                buildAndInsertDoneOrder(JSON)
            }
            else -> null
        }


    }

    fun buildAndInsertOpenOrder(JSON: JSONObject): OpenOrder {
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val order_id = JSON["order_id"].toString()
        val remainingTime = JSON["remaining_size"].toString()
        val time = JSON["time"].toString()
        val openOrder = OpenOrder(sequence, type, time, remainingTime, side, order_id, price)
        db.openOrderDao().insert(openOrder)
        return openOrder
    }

    fun buildAndInsertReceivedOrder(JSON: JSONObject): ReceivedOrder {
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val order_id = JSON["order_id"].toString()
        val remainingTime = JSON["remaining_size"].toString()
        val time = JSON["time"].toString()
        return ReceivedOrder(sequence, type, time, remainingTime, side, order_id, price)
    }

    fun buildAndInsertMatchOrder(JSON: JSONObject): MatchOrder {
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val makerOrderId = JSON["maker_order_id"].toString()
        val takerOrderId = JSON["taker_order_id"].toString()
        val tradeId = JSON["trade_id"] as Int
        val size = JSON["size"].toString()
        val time = JSON["time"].toString()
        val matchOrder = MatchOrder(sequence, type, tradeId, time, side, price, makerOrderId, takerOrderId, size)
        db.matchOrderDao().insert(matchOrder)
        return matchOrder
    }

    fun buildAndInsertDoneOrder(JSON: JSONObject): DoneOrder {
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val order_id = JSON["order_id"].toString()
        val remainingSize = JSON["remaining_size"].toString()
        val time = JSON["time"].toString()
        val reason = JSON["reason"].toString()
        val doneOrder = DoneOrder(sequence, type, time, order_id, side, price, reason, remainingSize)
        db.doneOrderDao().insert(doneOrder)
        return doneOrder
    }

    fun buildAndInsertChangeOrder(JSON: JSONObject): ChangeOrder {
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val newSize = JSON["new_size"].toString()
        val oldSize = JSON["old_size"].toString()
        val order_id = JSON["order_id"].toString()
        val time = JSON["time"].toString()
        val changeOrder = ChangeOrder(sequence, type, time, side, newSize, oldSize, price, order_id)
        db.changeOrderDao().insert(changeOrder)
        return changeOrder
    }

}