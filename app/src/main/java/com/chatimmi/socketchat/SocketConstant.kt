package com.chatimmi.socketchat

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.chatimmi.base.BaseActivitykt

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import java.util.concurrent.ExecutionException

class SocketConstant {
    private var context: Activity? = null
    private var mSocket: Socket? = null
    private var CHAT_SERVER_URL = "https://www.apoim.com:5000/"

/*    constructor(mSocket: Socket, context: BaseActivitykt?) {
        this.context = context
        this.mSocket = mSocket
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket.on(Socket.EVENT_RECONNECT, onReconnect)
        mSocket.connect()
    }*/

    fun getmSocket(mSocket: Socket, context: BaseActivitykt?) {
        try{
            if (!mSocket.connected())
                mSocket.connect()
            if (instance == null) {
                this.context = context
                this.mSocket = mSocket
                mSocket.on(Socket.EVENT_CONNECT, onConnect)
                mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
                mSocket.on(Socket.EVENT_RECONNECT, onReconnect)
                mSocket.connect()
            }
        }catch (e:ExecutionException){

        }

    }

    private val onConnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, "Connect", Toast.LENGTH_LONG).show() } }
    private val onDisconnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, "Disconnect", Toast.LENGTH_LONG).show() } }
    private val onReconnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, "Reconnect", Toast.LENGTH_LONG).show() } }
    private val onConnectError = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show() } }
    fun closeConnection() {
        if (mSocket != null) {
            mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.disconnect()
            mSocket!!.close()
        }
    }

    /**
     * Destroy.
     */
    fun destroy() {
        if (mSocket != null) {
            mSocket!!.off()
            mSocket!!.disconnect()
            mSocket!!.close()
            mSocket = null
        }
    }



        private var instance: SocketConstant? = null

        /**
         * Gets instance.
         *
         * @return the instance
         */
        @Synchronized
        fun getInstance(): SocketConstant? {
            if (instance == null) {
                instance = SocketConstant()
            }
            return instance

    }

    init {
        try {

            mSocket = IO.socket(CHAT_SERVER_URL)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("xzz", "erro: $e")
        }
    }
}