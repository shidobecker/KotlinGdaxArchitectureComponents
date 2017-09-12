package com.karchitecture.shido.karchitecture

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karchitecture.shido.karchitecture.extensions.e
import org.jetbrains.anko.button
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    val db: AppDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()

    val webSocket = GdaxWebSocket(db)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()
        webSocket.buildWebSocket()
        deleteRoom()
        e("Hello")
    }


    fun buildLayout() {
        relativeLayout {
            recyclerView {

            }
            button {
                onClick {
                    thread {
                        //Database access can't be in main thread since it may potentially lock the UI for a long period of time.
                        db.changeOrderDao().getAll().forEach { e(it) }
                        db.receivedOrderDao().getAll().forEach { e(it) }
                        db.matchOrderDao().getAll().forEach { e(it) }
                        db.openOrderDao().getAll().forEach { e(it) }
                        db.doneOrderDao().getAll().forEach { e(it) }
                    }
                }
                text = "Get all Orders"

            }
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
