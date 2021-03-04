package com.chatimmi.usermainfragment.connectfragment.chat

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    private val mPostsPerPage = 20
    private var mIsLoading = false
    private var mTotalItemCount = 0
    private var mLastVisibleItemPosition = 0
    private var isTyping = 1
    private var handler: Handler? = null


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
        handler = Handler()
         callMessageApi(userId.toString())

        val linearLayoutManager = LinearLayoutManager(this)
        binding!!.recyclerViews.layoutManager = linearLayoutManager

        binding!!.recyclerViews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // ly_popup_menu.setVisibility(View.GONE)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding!!.tvDaysStatus.visibility = View.GONE
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (linearLayoutManager.findFirstVisibleItemPosition() != -1) {
                    binding!!.tvDaysStatus.text = chatList!![linearLayoutManager.findFirstVisibleItemPosition()]!!.createdOn
                    binding!!.tvDaysStatus.visibility = View.VISIBLE
                }
                mTotalItemCount = linearLayoutManager.itemCount
                mLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (!mIsLoading && mTotalItemCount <= mLastVisibleItemPosition + mPostsPerPage) {
                    //getChat(chattingAdapter.getLastItemId());
                    mIsLoading = true
                }
                if (dx == 0 && dy == 0) {
                    binding!!.tvDaysStatus.visibility = View.GONE
                }
            }
        })
        binding!!.appBar.ivDots.setOnClickListener() {
            openMenuDialogBox()
        }
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
                scrollToBottom()
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
        isTyping()
        getIstypingUser()
        /* chat1= Chats()
       myUserId =chat1!!.uid*/
    }

    private fun scrollToBottom() {
        runOnUiThread {
            chattingAdapter!!.notifyDataSetChanged()
            binding!!.recyclerViews.scrollToPosition(chatList!!.size - 1)
        }
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

    //TODO Socket chat : isTyping user   image_FirebaseURL = imageUri;
    /*.....................isTyping.........................*/
    private fun isTyping() {
        binding!!.etSendMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // {senderName:senderName,sender_id:sender_id,channel_id:channel_id,chat_room_id:chat_room_id}
                if (isTyping == 1) {
                    val typing = JSONObject()
                    try {
                        typing.put("userID", myUserId)
                        mSocket.emit("typing", typing)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
                isTyping = 0
                handler!!.removeCallbacks(runnable)
                handler!!.postDelayed(runnable, 4000)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding!!.etSendMessage.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //got focus
            } else {
                // clearIsTyping()
                isTyping = 1
            }
        }
    }

    var runnable = Runnable {
        //clearIsTyping()
        isTyping = 1
    }

/*    private fun clearIsTyping() {
        val typing = JSONObject()
        try {
            if (chat1!!.chatRoomId != null && !chat1!!.chatRoomId.isEmpty()) {
                typing.put("senderName", myName)
                typing.put("sender_id", myUid)
                typing.put("channel_id", chat1!!.channelId)
                typing.put("chat_room_id", chat1!!.chatRoomId)
                mSocket.emit("clearTyping", typing)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }*/

    private fun getIstypingUser() {
        mSocket.on("typing") { args ->
            Log.e("typing", args[0].toString())
            val data = args[0] as JSONObject
            try {
                val userID = data.getString("userID")
                runOnUiThread {

                    if (userID == myUserId) {
                        val typingStatus = data.getString("is_typing")
                        if (typingStatus == "1") {
                            binding!!.appBar.tvShowTyping.visibility = View.VISIBLE
                            binding!!.appBar.tvShowTyping.text = "typing"
                        } else {
                            binding!!.appBar.tvShowTyping.visibility = View.GONE
                        }
                    }
                    /*     if (isTyping == 1) {

                             binding!!.appBar.tvShowTyping.visibility = View.VISIBLE
                             binding!!.appBar.tvShowTyping.text = "typing"
                         } else {
                             binding!!.appBar.tvShowTyping.visibility = View.GONE
                         }*/

                    // else binding!!.appBar.tvShowTyping.visibility = View.GONE

                }
            } catch (e: Exception) {

            }
        }
    }

    private fun openMenuDialogBox() {
        val view: View = layoutInflater.inflate(R.layout.alert_menu_dialog_box, null)
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.setContentView(view)
        val ll_blockUser = dialog.findViewById<LinearLayout>(R.id.ll_blockUser)
        val ll_deleteChat = dialog.findViewById<LinearLayout>(R.id.ll_deleteChat)
        val tv_cancel = dialog.findViewById<TextView>(R.id.tv_cancel)
        val tv_blockUser = dialog.findViewById<TextView>(R.id.tv_blockUser)
/*        if (blockedId == "both") {
            tv_blockUser!!.text = "Unblock User"
        } else if (blockedId == "") {
            tv_blockUser!!.text = "Block User"
        } else if (blockedId == venue_owner_id) {
            tv_blockUser!!.text = "Block User"
        } else if (blockedId == UserId) {
            tv_blockUser!!.text = "Unblock User"
        }*/
        ll_blockUser!!.setOnClickListener { view1: View? ->
            // blockChatDialog()
            dialog.dismiss()
        }
/*        ll_deleteChat!!.setOnClickListener { view1: View? ->
            //    deleteChatDialog();
            val customAlertDialog: CustomAlertDialog = object : CustomAlertDialog(this@TableChatActivity) {
                fun leftButtonClick() {
                    mDatabase.child(Constant.PROD).child("chat_room").child(id).child("delete").child(UserId).child("timeStamp").setValue(timeStamp)
                    map.clear()
                    // tv_days_status.setVisibility(View.GONE);
                    chatMsgList.clear()
                    tableChatAdapter.notifyDataSetChanged()
                    this.dismiss()
                }

                fun rightButtonClick() {
                    this.dismiss()
                }
            }*/
        /*      customAlertDialog.setMessage("Are you sure you want to delete all messages?")
              customAlertDialog.show()
              //Toast.makeText(this, "Delete", Toast.LENGTH_LONG).show();
              dialog.dismiss()
          }*/
        tv_cancel!!.setOnClickListener { view1: View? -> dialog.dismiss() }
        dialog.show()
    }
}