package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by mira on 11/09/2017.
 */
@Entity(tableName = "MATCH_ORDERS")
data class MatchOrder(@PrimaryKey
                      var sequence: Int = 0,
                      var type: String = "",
                      @SerializedName("trade_id")
                      var tradeId: Int = 0,
                      var time: String = "",
                      var side: String = "",
                      var price: String = "",
                      @SerializedName("maker_order_id")
                      var makerOrderId: String = "",
                      @SerializedName("taker_order_id")
                      var takerOrderId: String = "",
                      var size: String = "") {

    @Ignore constructor() : this(0)
}
