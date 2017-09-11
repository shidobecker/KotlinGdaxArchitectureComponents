package com.karchitecture.shido.karchitecture

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.karchitecture.shido.karchitecture.datas.dao.*
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder

/**
 * Created by Shido on 10/09/2017.
 */
@Database(entities = arrayOf(OpenOrder::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun openOrderDao(): OpenOrdersDao
    abstract fun changeOrderDao(): ChangeOrderDao
    abstract fun receivedOrderDao(): ReceivedOrdersDao
    abstract fun matchOrderDao(): MatchOrderDao
    abstract fun doneOrderDao(): DoneOrdersDao


}