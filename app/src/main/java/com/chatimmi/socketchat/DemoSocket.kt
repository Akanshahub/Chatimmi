package com.chatimmi.socketchat

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class DemoSocket {

    fun getSocket(): Socket? {
        var mSocket:Socket? = null
        return if (mSocket == null) {
            try {
                val opts = IO.Options()
                opts.reconnection = true
                mSocket = IO.socket(SocketConstant.CHAT_SERVER_URL, opts)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
                Log.d("nvkjdnkdnfk", "URISyntaxException: "+e.message)


            }
            mSocket
        } else mSocket
    }
}