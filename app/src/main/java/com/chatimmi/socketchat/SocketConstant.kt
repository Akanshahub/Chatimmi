package com.chatimmi.socketchat

import android.app.Activity
import android.widget.Toast
import com.chatimmi.BuildConfig
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import io.socket.client.Socket
import io.socket.emitter.Emitter



class SocketConstant {
    private var context: Activity? = null
    private var mSocket: Socket? = null

    constructor() {}
    constructor(mSocket: Socket, context: BaseActivitykt?) {
        this.context = context
        this.mSocket = mSocket
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket.on(Socket.EVENT_RECONNECT, onReconnect)
        mSocket.connect()
    }

    fun getmSocket(mSocket: Socket, context: Activity?) {
        if (!mSocket.connected()) mSocket.connect()
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
    }

    private val onConnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, R.string.connect, Toast.LENGTH_LONG).show() } }
    private val onDisconnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, R.string.disconnect, Toast.LENGTH_LONG).show() } }
    private val onReconnect = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, "Reconnect", Toast.LENGTH_LONG).show() } }
    private val onConnectError = Emitter.Listener { context!!.runOnUiThread { Toast.makeText(context, R.string.error_connect, Toast.LENGTH_LONG).show() } }
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

    companion object {
         val CHAT_SERVER_URL: String = "http://localhost:4000"
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
    }
}