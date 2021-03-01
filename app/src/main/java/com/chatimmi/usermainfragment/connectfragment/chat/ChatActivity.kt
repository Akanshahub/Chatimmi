package com.chatimmi.usermainfragment.connectfragment.chat

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChatBinding
import com.chatimmi.usermainfragment.connectfragment.Chats
import com.chatimmi.usermainfragment.connectfragment.ChattingAdapter
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ChatActivity : BaseActivitykt() {
    private var chattingAdapter: ChattingAdapter? = null
    private var chatList: ArrayList<Chats>? = null
    private var binding: ActivityChatBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_chat)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        // chatNode = gettingNotes();
        chatList = ArrayList<Chats>()
        chattingAdapter = ChattingAdapter(this, chatList)
        binding!!.ivSendMessageImage.setOnClickListener() {
            //toastMessage("fgdfvgvf", this)
            sendSocketMessage()
        }
        mSocket!!.on("new_msg", observeMessage)

    }


    private fun sendSocketMessage() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("message", binding!!.etSendMessage.text.toString().trim { it <= ' ' })
            jsonObject.put("email", "Amit@gmail.com")
            jsonObject.put("frontUserID", 338)
            jsonObject.put("userID", 238)
            jsonObject.put("userType", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket!!.emit("new_message", jsonObject)

    }

    private val observeMessage = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        try {
            toastMessage(data.getString("message"), this)
           /* val chat = Chats()
            chat.message = data.getString("message")
            binding!!.tv.text = chat.message*/
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}