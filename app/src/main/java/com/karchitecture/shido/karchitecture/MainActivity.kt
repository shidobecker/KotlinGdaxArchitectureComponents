package com.karchitecture.shido.karchitecture

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karchitecture.shido.karchitecture.extensions.e
import org.jetbrains.anko.button
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.JSONObject
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    lateinit var db: AppDatabase

    val webSocket = GdaxWebSocket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()
        webSocket.buildWebSocket()
        buildRoom()
        e("Hello")
    }


    fun buildLayout() {
        relativeLayout {
            button {
                onClick {
                    thread {
                        //Database access can't be in main thread since it may potentially lock the UI for a long period of time.
                        db.openOrderDao().getAll().forEach {
                            e(it)
                        }
                    }
                }
                text = "Get all Open Orders"

            }
        }
    }

    fun buildRoom() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, "GDAX").build()
        //Deleting all data before
        thread {
            with(db.openOrderDao()){
                delete(getAll())
            }
        }
    }


    fun buildAndInsertOpenOrder(JSON: JSONObject){


     //   db.openOrderDao().insert(OpenOrder(sequence, type, time, remainingTime, side, order_id, price))
    }





    override fun onDestroy() {
        webSocket.shutDown()
        super.onDestroy()
    }


}
