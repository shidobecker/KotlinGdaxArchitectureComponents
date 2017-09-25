package com.karchitecture.shido.karchitecture

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import com.karchitecture.shido.karchitecture.datas.dao.ChangeOrderDao
import com.karchitecture.shido.karchitecture.datas.model.ChangeOrder
import com.karchitecture.shido.karchitecture.datas.model.MatchOrder
import com.karchitecture.shido.karchitecture.extensions.e
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    val db: AppDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()

    val webSocket = GdaxWebSocket(db)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()
        deleteRoom()
        webSocket.buildWebSocket()

    }


    fun buildLayout() {
        val RECYCLER_VIEW_ID = 10
        val myAdapter = MyAdapter(this, db)
        relativeLayout {
            layoutParams = RelativeLayout.LayoutParams(matchParent, matchParent)
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)

            recyclerView {
                isFocusableInTouchMode = true
                id = RECYCLER_VIEW_ID
                adapter = myAdapter
                //RecyclerView is Observing this method which brings a list of LiveData MatchOrders that we can work with it
                db.matchOrderDao().loadMatchOrdersSync().observe(this@MainActivity, Observer {
                    e(it)
                    //it == List<MatchOrder>
                    if(it != null) {
                        val trades: List<Trade> = it.map { Trade(it.size.toFloat(), it.price.toFloat(), it.time, (it.side == "buy")) }//Mapping to a collection of trades
                        myAdapter.trades.clear()
                        myAdapter.trades.addAll(trades)
                        myAdapter.notifyDataSetChanged()
                    }

                })
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                layoutManager.isItemPrefetchEnabled = false

            }.lparams(matchParent, wrapContent){
                alignParentTop()
                bottomMargin = dip(20)
            }



            button {
                onClick {
                    thread {
                      //  forEveryMessage { db.changeOrderDao().getAll().e(it)  }
                        //Database access can't be in main thread since it may potentially lock the UI for a long period of time.
                        db.changeOrderDao().getAll().forEach { e(it) }
                        db.receivedOrderDao().getAll().forEach { e(it) }
                        db.matchOrderDao().getAll().forEach { e(it) }
                        db.openOrderDao().getAll().forEach { e(it) }
                        db.doneOrderDao().getAll().forEach { e(it) }
                    }
                }
                text = "Get all Orders"

            }.lparams(500, 150, {
                below(RECYCLER_VIEW_ID)
                centerInParent()
                topMargin = dip(20)
            })
        }



    }

    fun deleteRoom() {
        //Deleting all data before
        thread {
            db.openOrderDao().delete(db.openOrderDao().getAll())
            db.receivedOrderDao().delete(db.receivedOrderDao().getAll())
            db.matchOrderDao().delete(db.matchOrderDao().getAll())
            db.doneOrderDao().delete(db.doneOrderDao().getAll())
            db.changeOrderDao().delete(db.changeOrderDao().getAll())

        }
    }




    override fun onDestroy() {
        webSocket.shutDown()
        super.onDestroy()
    }


}


