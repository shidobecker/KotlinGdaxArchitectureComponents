package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by mira on 11/09/2017.
 */

@Entity(tableName = "CHANGE_ORDERS")
data class ChangeOrder(@PrimaryKey
                       var sequence: Int = 0,
                       var type: String = "",
                       var time: String = "",
                       var side: String = "",
                       @SerializedName("new_size")
                       var newSize: String = "",
                       @SerializedName("old_size")
                       var oldSize: String = "",
                       var price: String = "",
                       @SerializedName("order_id")
                       var orderId: String = "") {

    @Ignore constructor() : this(0)
}