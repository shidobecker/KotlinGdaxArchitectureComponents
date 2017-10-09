package com.karchitecture.shido.karchitecture.datas.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.karchitecture.shido.karchitecture.datas.model.DoneOrder
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder
import com.karchitecture.shido.karchitecture.datas.model.PriceSideTuple
import com.karchitecture.shido.karchitecture.datas.model.PriceTuple

/**
 * Created by Shido on 10/09/2017.
 */
@Dao
interface OpenOrdersDao {

    @Query("""SELECT PRICE "PRICE", SIDE "SIDE",
    SUM(remainingSize) "SUM"
    FROM OPEN_ORDERS
    GROUP BY PRICE, SIDE """)
    fun loadOpenOrdersSync(): LiveData<List<PriceSideTuple>>

    @Query("SELECT * FROM OPEN_ORDERS ORDER BY PRICE DESC LIMIT 1000")
    fun getAll(): List<OpenOrder>

    @Insert
    fun insert(event: OpenOrder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(events: List<OpenOrder>)

    @Delete
    fun delete(events: List<OpenOrder>)

   /* @Query(""""SELECT * FROM OPEN_ORDERS WHERE SIDE = "buy" ORDER BY PRICE DESC """)
    fun getBestBuy(): List<OpenOrder>
*/
    @Query("""SELECT PRICE "PRICE",
    SUM(remainingSize) "SUM"
    FROM OPEN_ORDERS
    WHERE SIDE = "buy"
    GROUP BY PRICE
    ORDER BY PRICE DESC""")
    fun getBids(): List<PriceTuple>


    @Query("""SELECT PRICE "PRICE",
    SUM(remainingSize) "SUM"
    FROM OPEN_ORDERS
    WHERE SIDE = "sell"
    GROUP BY PRICE
    ORDER BY PRICE ASC""")
    fun getAsks(): List<PriceTuple>


    @Query("""SELECT COUNT(1) FROM OPEN_ORDERS WHERE SIDE  = "Buy" """)
    fun getCount(): Int

    @Query("SELECT * FROM OPEN_ORDERS WHERE orderId = :orderId")
    fun getOrder(orderId: String): OpenOrder

    @Update
    fun updateOrder(order: OpenOrder)

    @Delete
    fun delete(event: OpenOrder)


}