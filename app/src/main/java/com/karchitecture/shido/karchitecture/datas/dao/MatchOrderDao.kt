package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.karchitecture.shido.karchitecture.datas.model.MatchOrder

/**
 * Created by mira on 11/09/2017.
 */
@Dao
interface MatchOrderDao {

    @Query("SELECT * FROM MATCH_ORDERS ORDER BY PRICE DESC")
    fun getAll(): List<MatchOrder>

    @Insert
    fun insert(match: MatchOrder)

    @Delete
    fun delete(matchs: List<MatchOrder>)

}