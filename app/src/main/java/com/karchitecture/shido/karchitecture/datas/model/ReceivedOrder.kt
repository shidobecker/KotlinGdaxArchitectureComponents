package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by mira on 11/09/2017.
 */
@Entity(tableName = "RECEIVED_ORDERS")
data class ReceivedOrder(@PrimaryKey
                         var sequence: Int = 0,
                         var type: String = "",
                         var time: String = "",
                         @SerializedName("order_id")
                         var orderId: String = "",
                         @SerializedName("order_type")
                         var orderType: String = "",
                         var side: String = "",
                         var price: String = "") {

    @Ignore constructor() : this(0)
}