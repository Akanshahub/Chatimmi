package com.chatimmi.usermainfragment.connectfragment.chat

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChatBinding
import com.chatimmi.usermainfragment.connectfragment.Chats
import com.chatimmi.usermainfragment.connectfragment.ChattingAdapter
import com.google.gson.JsonObject
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ChatActivity : BaseActivitykt() {
    private var chattingAdapter: ChattingAdapter? = null
    private var chatList: ArrayList<Chats>? = null
    private var binding: ActivityChatBinding? = null
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        // chatNode = gettingNotes();
        chatList = ArrayList<Chats>()
        chattingAdapter = ChattingAdapter(this, chatList)
        binding!!.ivSendMessageImage.setOnClickListener() {
            sendSocketMessage()
        }
        //mSocket=Chatimmi.getSocket()

        mSocket = Chatimmi.getSocket()!!

        if (::mSocket.isInitialized) {
            mSocket.on("new_msg", msgObserver)
            Log.d("cshxcjbhjb", "isInitialized:")
        } else {
            Log.d("cshxcjbhjb", "isInitialized: FALSEEE")
        }
//        mSocket.on("new_msg",Emitter.Listener { args ->
//            var data = args[0] as JSONObject
//            try {
//                Log.d("cshxcjbhjb", "message:$data")
//                toastMessage(data.getString("message"), this)
//                val chat = Chats()
//                chat.message = data.getString("message")
//                binding!!.tv.text = chat.message
//            } catch (e: JSONException) {
//                e.printStackTrace()
//                Log.d("cvvxcv", "message:${e.message}")
//            }
//        } )

        /*   mSocket?.on("new_msg") { args ->
               Log.d("cshxcjbhjb", "onCreate: ${args.size}")
               if (args[0] != null)
               {
                   var data = args[0] as JSONObject
                   Log.d("cshxcjbhjb", "message:${data.toString()}")
                   runOnUiThread {
                       Toast.makeText(this,"Data received from socket",Toast.LENGTH_SHORT).show()
                   }
               }
           }*/

    }


    private fun sendSocketMessage() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("message", binding!!.etSendMessage.text.toString().trim { it <= ' ' })
            jsonObject.put("email", "sachin@mail.com")
            jsonObject.put("frontUserID", 338)
            jsonObject.put("userID", 367)
            jsonObject.put("userType", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket!!.emit("new_message", jsonObject)

    }

    private var msgObserver = Emitter.Listener { args ->
        Log.d("cshxcjbhjb", "observeMessage:${args.size}")
        toastMessage("sl;axl;al;c;x", this)
        var data=JsonObject()

        /*  var data = args[0] as JSONObject
          Log.d("cshxcjbhjb", "message:$data")
            toastMessage(data.getString("message"), this)
                      val chat = Chats()
            chat.message = data.getString("message")
          binding!!.tv.text = chat.message*/
//        var data = args[0] as JSONObject
//        try {
//            Log.d("cshxcjbhjb", "message:$data")
//            toastMessage(data.getString("message"), this)
//            val chat = Chats()
//            chat.message = data.getString("message")
//            binding!!.tv.text = chat.message
//        } catch (e: JSONException) {
//            e.printStackTrace()
//            Log.d("cshxcjbhjb", "message:${e.message}")
//        }
    }


}