package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shido on 10/09/2017.
 */
@Entity(tableName = "OPEN_ORDERS")
data class OpenOrder(
    @PrimaryKey(autoGenerate = false)
    var sequence: Int = 0,
    var type: String = "",
    var time: String = "",
    var remainingSize: String ="",
    var side: String = "",
    var orderId: String ="",
    var price: String  = ""){
        @Ignore constructor() : this(0)
    }
