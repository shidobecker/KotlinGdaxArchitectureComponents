package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.DoneOrder
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder

/**
 * Created by Shido on 10/09/2017.
 */
@Dao
interface OpenOrdersDao {

    @Query("SELECT * FROM OPEN_ORDERS ORDER BY SEQUENCE")
    fun loadOpenOrdersSync(): LiveData<List<OpenOrder>>

    @Query("SELECT * FROM OPEN_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<OpenOrder>

    @Insert
    fun insert(event: OpenOrder)

    @Insert
    fun insert(events: List<OpenOrder>)

    @Delete
    fun delete(events: List<OpenOrder>)

    @Query("SELECT * FROM OPEN_ORDERS WHERE SIDE = \"buy\" ORDER BY PRICE DESC ")
    fun getBestBuy(): List<OpenOrder>


}