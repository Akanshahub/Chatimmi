package com.chatimmi.usermainfragment.connectfragment.chat

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChatBinding
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.usermainfragment.connectfragment.Chat
import com.chatimmi.usermainfragment.connectfragment.Chats
import com.chatimmi.usermainfragment.connectfragment.ChattingAdapter
import com.google.gson.Gson
import com.google.gson.JsonElement
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ChatActivity : BaseActivitykt() {

    private var chatList: ArrayList<Chat.Data.MessageData?>? = null
    private var binding: ActivityChatBinding? = null
    lateinit var mSocket: Socket
    private var chat1: Chats? = null
    private var chattingAdapter: ChattingAdapter? = null
    lateinit var session: Session
    var groupName = ""
    private var userId = 0
    var categoryName = ""
    var subCategoryName = ""
    var avatar: String = ""
    var emailId: String = ""
    var myUserId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        session = Session(this)
        // chatNode = gettingNotes();
        //chatList = ArrayList<Chat.Data.MessageData>()


        groupName = intent.getStringExtra("groupName").toString()
        categoryName = intent.getStringExtra("categoryName").toString()
        subCategoryName = intent.getStringExtra("subCategoryName").toString()
        userId = intent.getIntExtra("userId", 0)
        avatar = intent.getStringExtra("avatar").toString()
        emailId = intent.getStringExtra("emailId").toString()
        myUserId = session.getUserData()!!.data!!.user_details.userID.toString()
        binding!!.appBar.tvTitle.text = groupName
        Glide.with(binding!!.appBar.image.context).load(avatar).into(binding!!.appBar.image)
        binding!!.ivSendMessageImage.setOnClickListener() {
            sendSocketMessage()
        }
        mSocket = Chatimmi.getSocket()!!
        val jsonObject = JSONObject()
        try {
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        callMessageApi(userId.toString())



        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("join", jsonObject)

        mSocket.on("new_msg", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val chat = Chat.Data.MessageData()
            chat.message = data.getString("message")
            // chat.uid = data.getString("frontUserID")
            Log.d("cshxcjbhjb", " message:$data")
            Log.d("fnkanfkla", "${chat.message} ====:")

            chatList!!.add(chat)
            chattingAdapter!!.setMyId(myUserId)
            runOnUiThread {
                chattingAdapter!!.notifyDataSetChanged()
            }
        })
        binding!!.appBar.ivBtnBack.setOnClickListener() {
            onBackPressed()

        }
        mSocket.on("new_message", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val gson = Gson().fromJson<Chat.Data.MessageData>(data.toString(), Chat.Data.MessageData::class.java)


            //val chat = Chat.Data.MessageData()


            Log.d("cshxcjbhjb", " message:$data")
            /*chat.message = data.getString("message")
           chat.senderId = data.getString("sender_id")*/
            // Log.d("fnkanfkla", "${chat.message} ====:")
            chatList!!.add(gson)
            chattingAdapter!!.setMyId(myUserId)
            runOnUiThread {
                chattingAdapter!!.notifyDataSetChanged()
            }


        })
        /* chat1= Chats()
       myUserId =chat1!!.uid*/
    }


    private fun sendSocketMessage() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("message", binding!!.etSendMessage.text.toString().trim { it <= ' ' })
            jsonObject.put("email", emailId)
            jsonObject.put("frontUserID", userId)
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
            jsonObject.put("userType", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("new_message", jsonObject)
        binding!!.etSendMessage.setText("")
    }

    fun callMessageApi(userId: String) {

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.getMessageApi(userId)
        callApi!!.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val gson = Gson().fromJson<Chat>(response.body().toString(), Chat::class.java)
                    chatList = gson.data?.messageData
                    chattingAdapter = ChattingAdapter(this@ChatActivity, chatList, myUserId)
                    binding!!.recyclerViews.adapter = chattingAdapter


                } else {
                    Log.d("haxhskm", "onResponse: ${response.errorBody()}")

                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }

}