package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder

/**
 * Created by Shido on 10/09/2017.
 */
@Dao
interface OpenOrdersDao {

    @Query("SELECT * FROM OPEN_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<OpenOrder>

    @Insert
    fun insert(event: OpenOrder)

    @Delete
    fun delete(events: List<OpenOrder>)


}