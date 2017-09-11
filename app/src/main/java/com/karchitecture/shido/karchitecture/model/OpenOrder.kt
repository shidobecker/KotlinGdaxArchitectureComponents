package com.karchitecture.shido.karchitecture.model

import android.arch.persistence.room.ColumnInfo
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
    var remaining_size: String ="",
    var side: String = "",
    var order_id: String ="",
    var price: String  = ""){
        @Ignore constructor() : this(0)
    }
