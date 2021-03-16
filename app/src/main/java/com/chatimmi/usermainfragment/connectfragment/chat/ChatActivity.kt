package com.chatimmi.usermainfragment.connectfragment.chat

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.webkit.MimeTypeMap
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChatBinding
import com.chatimmi.helper.ImagePicker
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.usermainfragment.connectfragment.Chat
import com.chatimmi.usermainfragment.connectfragment.Chats
import com.chatimmi.usermainfragment.connectfragment.ChattingAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.yalantis.ucrop.UCrop
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : BaseActivitykt() {

    private var chatList: ArrayList<Chat.Data.MessageData?>? = null
    private var binding: ActivityChatBinding? = null
    lateinit var mSocket: Socket
    private var chat1: Chats? = null
    private var chattingAdapter: ChattingAdapter? = null
    lateinit var session: Session
    var oppUserName = ""
    private var oppUserId = 0
    var categoryName = ""
    var subCategoryName = ""
    var avatar: String = ""
    var emailId: String = ""
    var myUserId = ""
    var BlockStatus = ""
    var isOnline = ""
    var userType = "1"
    var file: File? = null
    private val mPostsPerPage = 20
    private var mIsLoading = false
    private var mTotalItemCount = 0
    private var mLastVisibleItemPosition = 0
    private var isTyping = 1
    private var handler: Handler? = null
    private var previousDay = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        session = Session(this)
        // chatNode = gettingNotes();
        //chatList = ArrayList<Cheat.Data.MessageData>()


        oppUserName = intent.getStringExtra("groupName").toString()
  /*      categoryName = intent.getStringExtra("categoryName").toString()
        subCategoryName = intent.getStringExtra("subCategoryName").toString()*/
        oppUserId = intent.getIntExtra("userId", 0)
        avatar = intent.getStringExtra("avatar").toString()
        emailId = intent.getStringExtra("emailId").toString()
        myUserId = session.getUserData()!!.data!!.user_details.userID.toString()
        binding!!.appBar.tvTitle.text = oppUserName
        Glide.with(binding!!.appBar.image.context).load(avatar).into(binding!!.appBar.image)
        binding!!.ivSendMessageImage.setOnClickListener() {


            if (binding!!.etSendMessage.text.toString().trim().isNotEmpty()) {
                sendSocketMessage()
            } else {
                toastMessage("Please Enter Message", this)
            }
        }
        binding!!.ivSearchIconChat.setOnClickListener() {
            // toastMessage("Sdscdxz",this)
            // selectImage(this)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this@ChatActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 99)
                } else {
                    ImagePicker.pickImage(this@ChatActivity)
                }
            } else {
                ImagePicker.pickImage(this@ChatActivity)
            }
        }

        mSocket = Chatimmi.getSocket()!!
        val jsonObject = JSONObject()
        try {
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        handler = Handler()
        callMessageApi(oppUserId.toString())

        val linearLayoutManager = LinearLayoutManager(this)
        binding!!.recyclerViews.layoutManager = linearLayoutManager
        // binding!!.recyclerViews.scrollToPosition(chatList!!.size - 1)
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

                    val dateString: String = chatList!![linearLayoutManager.findFirstVisibleItemPosition()]!!.createdOn!!
                    if (dateString.isNotEmpty()) {
                        Log.d("Dlpsdpos", "onScrolled: " + chatList!![linearLayoutManager.findFirstVisibleItemPosition()]!!.createdOn!!)
                        getYesterdayDate(dateString, binding!!.tvDaysStatus)
                        binding!!.tvDaysStatus.visibility = View.VISIBLE

                    }

/*                    val dateString: String = chatList!![linearLayoutManager.findFirstVisibleItemPosition()]!!.createdOn!!
                    Log.d("Dlpsdpos", "onScrolled: " + chatList!![linearLayoutManager.findFirstVisibleItemPosition()]!!.createdOn!!)
                    getYesterdayDate(dateString, binding!!.tvDaysStatus)
                    binding!!.tvDaysStatus.visibility = View.VISIBLE*/
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
            Log.d("cshxcjbhjb", " message:$data")
            Log.d("fnkanfkla", "${chat.message} ====:")

            chatList!!.add(chat)
            chattingAdapter!!.setMyId(myUserId)
            runOnUiThread {

                chattingAdapter!!.notifyDataSetChanged()
                binding!!.recyclerViews.scrollToPosition(chatList!!.size - 1)

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
            chatList!!.add(gson)
            chattingAdapter!!.setMyId(myUserId)
            runOnUiThread {
                chattingAdapter!!.notifyDataSetChanged()
                binding!!.recyclerViews.scrollToPosition(chatList!!.size - 1)
            }


        })


        //updateUicc()
        isTyping()
        getTypingStatus()
        readUndreadMessage()
        //getIstypingUser()
        clearIsTyping()

        getOnlineStatus()

    }


    private fun sendSocketMessage() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("message", binding!!.etSendMessage.text.toString().trim { it <= ' ' })
            jsonObject.put("email", emailId)
            jsonObject.put("frontUserID", oppUserId)
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
            jsonObject.put("is_image", "0")
            jsonObject.put("userType", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("new_message", jsonObject)
        binding!!.etSendMessage.setText("")
    }

    private fun deleteChat() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("frontUserID", oppUserId)
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("delete_chat", jsonObject)
        callMessageApi(oppUserId.toString())
    }


    fun callMessageApi(userId: String) {

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.getMessageApi(userId)
        callApi!!.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    chatList = ArrayList()

                    val gson = Gson().fromJson<Chat>(response.body().toString(), Chat::class.java)
                    Log.d("haxhskm", "onResponse: ${response.body()}")
                    BlockStatus = gson.data!!.isBlock.toString()

                    isOnline = gson.data!!.isOnline.toString()
                    showOnlineOffline()
                    for (getData in gson.data?.messageData!!) {
                        val day = getYesterdayDateV2(getData?.createdOn!!)
                        getData.shouldVisibleShowDateView = getYesterdayDateV2(getData.createdOn!!) != previousDay

                        getData.dayName = day
                        previousDay = day
                        chatList?.add(getData)
                    }
                    // chatList = gson.data?.messageData
                    chattingAdapter = ChattingAdapter(this@ChatActivity, chatList, myUserId, { timestamp -> }, true, this@ChatActivity)
                    binding!!.recyclerViews.adapter = chattingAdapter
                    Log.d("fnkasnfkas", "onResponse: SIZE ${chatList?.size}")

                    scrollToBottom()
                    mSocket.on("read", Emitter.Listener { args ->

                        val data = args[0] as JSONObject
                        try {
                            val readBy = data.getString("read_by")
                            val readTo = data.getString("read_to")
                            if (readBy == oppUserId.toString() && readTo == myUserId) {
                                val isRead = data.getInt("is_read")
                                for (i in 0 until chatList!!.size) {
                                    if (isRead == 1) {
                                        chatList!![i]!!.isread = 1
                                    }
                                }
                                runOnUiThread {
                                    chattingAdapter!!.notifyDataSetChanged()

                                }
                            }


                        } catch (e: Exception) {
                            Log.d("hjghuhhjh", "Exception:====== ${e.message}")
                        }

                    })


                } else {
                    Log.d("haxhskm", "onResponse: ${response.errorBody()}")

                }
                // getOnlineStatus()
                onForBlockUnBlockOpponent()
                updateUi()
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })


    }

    val flag = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            userType
    )

    private fun scrollToBottom() {
        runOnUiThread {
            //chattingAdapter!!.notifyDataSetChanged()
            binding!!.recyclerViews.scrollToPosition(chatList!!.size - 1)

        }
    }

    fun callUploadChatImageApi(confirm_password: RequestBody, profilePicture: MultipartBody.Part?) {

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callUploadChatImageApi(confirm_password, profilePicture)
        callApi.enqueue(object : Callback<UploadImage> {
            override fun onResponse(call: Call<UploadImage>, response: Response<UploadImage>) {
                if (response.isSuccessful) {

                    //  val gson = Gson().fromJson<UploadImage>(response.body().toString(), Chat::class.java)
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("message", response.body()!!.data!!.avatar)
                        jsonObject.put("email", emailId)
                        jsonObject.put("frontUserID", oppUserId)
                        jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)
                        jsonObject.put("is_image", "1")
                        jsonObject.put("userType", "1")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    Log.d("jusiduuisd", "onResponse: ${jsonObject}")
                    mSocket = Chatimmi.getSocket()!!
                    mSocket.emit("new_message", jsonObject)


                    toastMessage(response.message(), this@ChatActivity)
                    // Log.d("haxhskm", "onResponse: ${response()}")

                } else {
                    toastMessage(response.message(), this@ChatActivity)
                    Log.d("haxhskm", "onResponse: ${response.errorBody()}")

                }

            }

            override fun onFailure(call: Call<UploadImage>, t: Throwable) {

            }
        })
    }

    private fun readUndreadMessage() {

        val jsonObject = JSONObject()
        try {
            jsonObject.put("read_to", oppUserId)
            jsonObject.put("read_by", session.getUserData()!!.data!!.user_details.userID)
            jsonObject.put("is_read", 1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("read", jsonObject)
    }


    //TODO Socket chat : isTyping user   image_FirebaseURL = imageUri;
    /*.....................isTyping.........................*/
    private fun isTyping() {
        binding!!.etSendMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // {senderName:senderName,sender_id:sender_id,channel_id:channel_id,chat_room_id:chat_room_id}
                /*  if (isTyping == 1) {*/
                val typing = JSONObject()
                try {
                    typing.put("userID", myUserId)
                    typing.put("frontUserID", oppUserId)
                    typing.put("is_typing", 1)
                    mSocket.emit("typing", typing)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }


                isTyping = 0
                handler!!.removeCallbacks(runnable)
                handler!!.postDelayed(runnable, 4000)
            }

            override fun afterTextChanged(editable: Editable) {
                val params = binding!!.relative.layoutParams

                if (binding!!.etSendMessage.lineCount == 1) {
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT
                } else if (binding!!.etSendMessage.lineCount == 2) {
                    params.height = 130
                } else if (binding!!.etSendMessage.lineCount == 3) {
                    params.height = 200
                } else if (binding!!.etSendMessage.lineCount > 3) {
                    params.height = 200
                }

                binding!!.relative.layoutParams = params


            }
        })
        binding!!.etSendMessage.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //got focus
            } else {
                clearIsTyping()
                isTyping = 1
            }
        }
    }

    var runnable = Runnable {
        clearIsTyping()
        isTyping = 1
    }

    private fun clearIsTyping() {
        val typing = JSONObject()
        try {

            typing.put("userID", myUserId)
            typing.put("frontUserID", oppUserId)
            typing.put("is_typing", 0)
            mSocket.emit("typing", typing)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun getTypingStatus() {
        Chatimmi.mSocket!!.on("typing") { args ->
            Log.e("typing", args[0].toString())
            val data = args[0] as JSONObject
            try {
                activity.runOnUiThread {
                    val myUserID = data.getString("frontUserID")
                    val oppUserIds = data.getString("userID")


                    if (oppUserIds == oppUserId.toString() && myUserID == myUserId) {
                        val typingStatus = data.getInt("is_typing")
                        if (typingStatus == 1) {
                            binding!!.appBar.tvShowTyping.visibility = View.VISIBLE
                            binding!!.appBar.tvShowTyping.text = "typing..."
                        } else {
                            binding!!.appBar.tvShowTyping.visibility = View.GONE
                        }
                    }


                }
            } catch (e: Exception) {

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val uri = UCrop.getOutput(data!!)

            data.let {
                Log.d("fbasjfbajsfa", "onActivityResult: ${uri}")
            }
            file = getImageFile(uri!!)
            var profilePicture: MultipartBody.Part? = null
            if (file != null) {

                val file: File = file!!

                val reqFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val body: MultipartBody.Part = MultipartBody.Part.createFormData("file_name", file.name, reqFile)

                profilePicture = body

//            = MultipartBody.Part.createFormData(
//                    "profilePicture", file.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
            }

            callUploadChatImageApi(flag, profilePicture)
            // temPhoto = uri
            // viewModel.imageUri = uri!!

            var bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)


            try {
                showImage(uri)

                bitmap = getBitmapFromUri(uri)
                //avatarUri = uri
                // mimeType = getMimeType(avatarUri)

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("erroris", e.printStackTrace().toString())
            }
        } else if (requestCode == ImagePicker.PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("fbasjfbajsfa", "PICK_IMAGE_REQUEST_CODE: ")

            val uri: Uri = ImagePicker.getImageURIFromResult(this@ChatActivity, requestCode, resultCode, data)
            openCropActivity(uri)
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            val result = UCrop.getError(data!!)
            val error = result!!.message
        }
    }

    private fun getImageFile(uri: Uri): File {
        val bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, uri)

        val f = File(cacheDir, "filename");
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        Log.d("fffdfdffkkf", "MultipartBody: ${f.name}")
        return f
    }

    private fun showImage(imageUri: Uri) {
        // binding.ivImages.setImageURI(imageUri)
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }


    private fun openCropActivity(sourceUri: Uri) {
        val time: Long = System.currentTimeMillis()
        val str = "" + time
        val destinationPath = str + "temp.jpg"
        val options: UCrop.Options = UCrop.Options()
        options.setHideBottomControls(true)
        UCrop.of(sourceUri, Uri.fromFile(File(cacheDir, destinationPath))).withAspectRatio(4F, 3F).withOptions(options).start(this)
    }


    private fun getMimeType(uri: Uri?): String? {
        var mimeType: String? = null
        mimeType = if (uri!!.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr = contentResolver
            cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase())
        }
        return mimeType
    }

    private fun openMenuDialogBox() {
        val view: View = layoutInflater.inflate(R.layout.alert_menu_dialog_box, null)

        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.setContentView(view)
        val ll_blockUser = dialog.findViewById<RelativeLayout>(R.id.ll_blockUser)
        val ll_deleteChat = dialog.findViewById<RelativeLayout>(R.id.ll_deleteChat)
        val ll_muteChat = dialog.findViewById<RelativeLayout>(R.id.ll_mute)
        val tv_cancel = dialog.findViewById<TextView>(R.id.tv_cancel)
        val tv_blockUser = dialog.findViewById<TextView>(R.id.tv_blockUser)

        if (BlockStatus == "0") {
            tv_blockUser!!.text = "Block"
        } else if (BlockStatus == "1") {
            tv_blockUser!!.text = "UnBlock"
        } else if (BlockStatus == "2") {
            tv_blockUser!!.text = "Block"
        } else if (BlockStatus == "3") {
            tv_blockUser!!.text = "UnBlock"
        }
        ll_blockUser!!.setOnClickListener { view1: View? ->
            //blockUnBlockOpponent()
            alertBlockUnBlockDailog()
            callMessageApi(oppUserId.toString())
            dialog.dismiss()
            // blockChatDialog()
            // dialog.dismiss()
        }
        ll_deleteChat!!.setOnClickListener { view1: View? ->
            alertDailog()
            dialog.dismiss()


        }

        tv_cancel!!.setOnClickListener { view1: View? -> dialog.dismiss() }
        dialog.show()
    }

    fun alertDailog() {
        val mAlertDialog = android.app.AlertDialog.Builder(this)

        mAlertDialog.setTitle("Alert") //set alertdialog title
        mAlertDialog.setMessage("Are you sure you want delete chat")
        //set alertdialog message
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            deleteChat()
            chatList!!.clear()
            chattingAdapter!!.notifyDataSetChanged()
            dialog.dismiss()
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
            dialog.dismiss()
        }
        mAlertDialog.show()
    }

    fun alertBlockUnBlockDailog() {
        val mAlertDialog = android.app.AlertDialog.Builder(this)
        mAlertDialog.setTitle("Alert") //set alertdialog title

        if (BlockStatus == "0") {
            mAlertDialog.setMessage("Do you want to block?")
        } else if (BlockStatus == "1") {
            mAlertDialog.setMessage("Do you want to unblock?")
        } else if (BlockStatus == "2") {
            mAlertDialog.setMessage("Do you want to block?")
        } else if (BlockStatus == "3") {
            mAlertDialog.setMessage("Do you want to unblock?")
        }
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            blockUnBlockOpponent()
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
            dialog.dismiss()
        }
        mAlertDialog.show()
    }

    fun blockUnBlockOpponent() {
        val jsonObject = JSONObject()
        var isBlock = ""
        try {
            if (BlockStatus == "0") {
                isBlock = "1"
            } else if (BlockStatus == "1") {
                isBlock = "0"
            } else if (BlockStatus == "2") {
                isBlock = "3"
            } else if (BlockStatus == "3") {
                isBlock = "2"
            }
            jsonObject.put("frontUserID", oppUserId)
            jsonObject.put("block_flag", isBlock)
            jsonObject.put("userID", session.getUserData()!!.data!!.user_details.userID)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("jusiduuisd", "onResponse: ${jsonObject}")
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("block_user", jsonObject)

    }


    fun onForBlockUnBlockOpponent() {
        Chatimmi.mSocket!!.on("block_user") { args ->
            Log.e("block_user", args[0].toString())
            val data = args[0] as JSONObject
            try {
                activity.runOnUiThread {
                    val myUserID = data.getString("userID")
                    val oppUserIds = data.getString("frontUserID")
                    if (oppUserIds == oppUserId.toString() && myUserID == myUserId) {
                        BlockStatus = data.getString("is_block")
                        updateUi()
                    } else if (oppUserIds == myUserId && myUserID == oppUserId.toString()) {
                        BlockStatus = data.getString("is_block")
                        updateUi()
                    }
                }


            } catch (e: Exception) {

            }
        }


    }

    fun getOnlineStatus() {
        Chatimmi.mSocket!!.on("userStatus") { args ->
            Log.e("userStatus", args[0].toString())
            val data = args[0] as JSONObject
            try {
                activity.runOnUiThread {

                    val oppUserIds = data.getString("userID")
                    if (oppUserIds == oppUserId.toString()) {
                        isOnline = data.getString("is_online")
                        showOnlineOffline()
                    }
                }


            } catch (e: Exception) {

            }
        }
    }

    fun showOnlineOffline() {
        if (isOnline == "1") {
            binding!!.appBar.llIsOnlineUser.visibility = View.VISIBLE
        } else {
            binding!!.appBar.llIsOnlineUser.visibility = View.GONE

        }
    }

    fun updateUi() {
        if (BlockStatus == "0") {
            binding!!.rlFooterSendMessage.visibility = View.VISIBLE
            binding!!.tvBlockText.visibility = View.GONE
        } else if (BlockStatus == "1") {
            binding!!.rlFooterSendMessage.visibility = View.GONE
            binding!!.tvBlockText.visibility = View.VISIBLE
            binding!!.tvBlockText.text = "You have blocked $oppUserName. Can't send any message"
        } else if (BlockStatus == "2") {
            binding!!.rlFooterSendMessage.visibility = View.GONE
            binding!!.tvBlockText.visibility = View.VISIBLE
            binding!!.tvBlockText.text = "You are blocked by $oppUserName. Can't send any message"
        } else if (BlockStatus == "3") {
            binding!!.rlFooterSendMessage.visibility = View.GONE
            binding!!.tvBlockText.visibility = View.VISIBLE
            binding!!.tvBlockText.text = "You have blocked each other. Can't send any message"
        }
    }
}