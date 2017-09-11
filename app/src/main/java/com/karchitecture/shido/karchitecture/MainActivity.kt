package com.karchitecture.shido.karchitecture

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.karchitecture.shido.karchitecture.extensions.e
import com.karchitecture.shido.karchitecture.model.OpenOrder
import okhttp3.*
import okio.ByteString
import org.jetbrains.anko.relativeLayout
import java.util.concurrent.TimeUnit
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.JSONObject
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var webSocket: WebSocket
    lateinit var client: OkHttpClient
    lateinit var request: Request
    lateinit var listener: WebSocketListener
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()
        buildWebSocket()
        buildRoom()
        e("Hello")
        webSocket = client.newWebSocket(request, listener)


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

    fun buildWebSocket() {
        client = OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build()

        request = Request.Builder()
                .url("wss://ws-feed.gdax.com")
                .build()


        listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                e("On Open: $response")
                e("On Open: ${response.message()}")
                webSocket.send("""{"type": "subscribe","product_ids": ["ETH-USD"]}""")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                e("MESSAGE1: " + text)
                val JSON = JSONObject(text)
                val type = JSON["type"].toString()
                when (type){
                    "open" ->{
                        buildAndInsertOpenOrder(JSON)
                    }
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                e("MESSAGE2: " + bytes.hex())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                e("CLOSE: $code $reason")
            }

            override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
                e("Websocket on failure: ${webSocket?.queueSize()}")
                e("Throwable message: ${t?.message}")
                e("On Failure response body : ${response?.body()}")
                t?.printStackTrace()
            }
        }
    }

    fun buildAndInsertOpenOrder(JSON: JSONObject){
        val type = JSON["type"].toString()
        val sequence = JSON["sequence"] as Int
        val side = JSON["side"].toString()
        val price = JSON["price"].toString()
        val order_id = JSON["order_id"].toString()
        val remainingTime = JSON["remaining_size"].toString()
        val time = JSON["time"].toString()

        db.openOrderDao().insert(OpenOrder(sequence, type, time, remainingTime, side, order_id, price))
    }





    override fun onDestroy() {
        webSocket.close(1000, null)
        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown()
        super.onDestroy()
    }


}
