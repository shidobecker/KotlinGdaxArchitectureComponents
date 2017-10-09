package com.karchitecture.shido.karchitecture.datas.model

import android.arch.persistence.room.ColumnInfo

/**
 * Created by Shido on 08/10/2017.
 */
data class PriceTuple(
        @ColumnInfo(name = "PRICE") var price: Float,
        @ColumnInfo(name = "SUM") var sum: Float
)

data class PriceSideTuple(
    @ColumnInfo(name = "SIDE") var side: String,
    @ColumnInfo(name = "PRICE") var price: Float,
    @ColumnInfo(name = "SUM") var sum: Float
)