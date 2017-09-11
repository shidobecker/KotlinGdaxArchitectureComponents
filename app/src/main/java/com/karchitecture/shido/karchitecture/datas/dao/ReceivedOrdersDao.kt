package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.ReceivedOrder

/**
 * Created by mira on 11/09/2017.
 */
@Dao
interface ReceivedOrdersDao {

    @Query("SELECT * FROM RECEIVED_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<ReceivedOrder>

    @Insert
    fun insert(event: ReceivedOrder)

    @Delete
    fun delete(events: List<ReceivedOrder>)

}