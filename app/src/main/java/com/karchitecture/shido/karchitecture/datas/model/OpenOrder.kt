package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Shido on 10/09/2017.
 */
@Entity(tableName = "OPEN_ORDERS")
data class OpenOrder(
    var sequence: Long = 0,
    var type: String = "",
    var time: String = "",
    @SerializedName("remaining_size")
    var remainingSize: Double = 0.0,
    var side: String = "",
    @SerializedName("order_id")
    @PrimaryKey(autoGenerate = false)
    var orderId: String ="",
    var price: Float = 0f){
        @Ignore constructor() : this(0)
    }
