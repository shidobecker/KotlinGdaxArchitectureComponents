package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.ChangeOrder

/**
 * Created by mira on 11/09/2017.
 */
@Dao
interface ChangeOrderDao {

    @Query("SELECT * FROM CHANGE_ORDERS ORDER BY SEQUENCE")
    fun loadChangeOrdersSync(): LiveData<List<ChangeOrder>>

    @Query("SELECT * FROM CHANGE_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<ChangeOrder>

    @Insert
    fun insert(change: ChangeOrder)

    @Delete
    fun delete(changes: List<ChangeOrder>)

}