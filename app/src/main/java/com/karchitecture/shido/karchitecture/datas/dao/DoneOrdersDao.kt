package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.ChangeOrder
import com.karchitecture.shido.karchitecture.datas.model.DoneOrder

/**
 * Created by mira on 11/09/2017.
 */
@Dao
interface DoneOrdersDao {

    @Query("SELECT * FROM DONE_ORDERS ORDER BY SEQUENCE")
    fun loadDoneOrdersSync(): LiveData<List<DoneOrder>>


    @Query("SELECT * FROM DONE_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<DoneOrder>

    @Insert
    fun insert(done: DoneOrder)

    @Delete
    fun delete(dones: List<DoneOrder>)

}