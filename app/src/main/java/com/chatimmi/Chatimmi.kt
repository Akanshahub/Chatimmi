package com.chatimmi


import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.chatimmi.app.pref.Session
import com.chatimmi.socketchat.SocketConstant
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import java.security.MessageDigest


class Chatimmi : Application() {

    var instance: Chatimmi? = null
    private lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        instance = this

        getKeyHashFacebook()
        getKeyHashFacebook()
        if (Session(this@Chatimmi).getIsUserLoggedIn() != "yes") {
            if(Session(this@Chatimmi).getUserData()!!.data!!.user_details!=null) {
                authorization = Session(this@Chatimmi).getAuthToken()
            }

        }

    }




    companion object {
        public var mSocket: Socket? = null
        fun getSocket(): Socket? {
            Log.d("nvkjdnkdnfk", "object: ")
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
            } else  mSocket
        }
        var groupFilterResponse: GroupFilterResponse? = null
        var authorization = ""

    }

    private fun getKeyHashFacebook() {
        try {
            val info = packageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

