package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by mira on 11/09/2017.
 */
@Entity(tableName = "MATCH_ORDERS")
data class MatchOrder(@PrimaryKey
                      var sequence: Int = 0,
                      var type: String = "",
                      var tradeId: Int = 0,
                      var time: String = "",
                      var side: String = "",
                      var price: String = "",
                      var makerOrderId: String = "",
                      var takerOrderId: String = "",
                      var size: String = "") {

    @Ignore constructor() : this(0)
}
