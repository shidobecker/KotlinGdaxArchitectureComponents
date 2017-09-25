package com.karchitecture.shido.karchitecture

import com.karchitecture.shido.karchitecture.extensions.e
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Created by mira on 11/09/2017.
 */
class GdaxWebSocket (val db: AppDatabase) {
    lateinit var webSocket: WebSocket
    lateinit var client: OkHttpClient
    lateinit var request: Request
    lateinit var listener: WebSocketListener

    val messageParser = MessageParser(db)

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
                messageParser.readMessage(text)
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
        webSocket = client.newWebSocket(request, listener)

    }

    fun shutDown(){
        webSocket.close(1000, null)
        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown()
    }

}