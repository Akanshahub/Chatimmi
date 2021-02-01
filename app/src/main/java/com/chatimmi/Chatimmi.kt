package com.chatimmi

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import com.chatimmi.socketchat.SocketConstant
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class Chatimmi : Application() {
    private var mSocket: Socket? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext()
        printHash()

    }

    fun getSocket(): Socket? {
        return if (mSocket == null) {
            try {
                val opts = IO.Options()
                opts.reconnection = true
                mSocket = IO.socket(SocketConstant.CHAT_SERVER_URL, opts)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            mSocket
        } else mSocket
    }

    companion object {
        private lateinit var context: Context
        var instance: Chatimmi? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    private fun printHash() {
        val bytesPlayStore = byteArrayOf(0xE1.toByte(), 0x4C, 0x96.toByte(), 0xB1.toByte(), 0x52, 0x8E.toByte(), 0x61, 0xC2.toByte(), 0x7D, 0x0A, 0xDB.toByte(), 0x85.toByte(), 0x0A, 0x4B, 0x26, 0x67, 0xFA.toByte(), 0x5B, 0x92.toByte(), 0x00)
        Log.d("HashPlayStore", Base64.encodeToString(bytesPlayStore, Base64.NO_WRAP))
        val bytesLocal = byteArrayOf(0x2A, 0x7B, 0x61, 0x8C.toByte(), 0x26, 0xFE.toByte(), 0x07, 0x5E, 0x95.toByte(), 0xA3.toByte(), 0x79, 0xCE.toByte(), 0x57, 0x27, 0x66, 0x10, 0xBF.toByte(), 0x7A, 0x0C, 0x9E.toByte())
        Log.d("HashLocal", Base64.encodeToString(bytesLocal, Base64.NO_WRAP))
    }
}

