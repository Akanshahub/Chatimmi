package com.chatimmi.usermainfragment.connectfragment.chat

import android.Manifest
import android.content.ContentResolver
import android.content.Context
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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
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
    var userType = "1"
    var file: File? = null
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


            if (binding!!.etSendMessage.getText().toString().isNotEmpty()) {
                sendSocketMessage()
            }else{
                toastMessage("Please Enter Message",this)
            }
           // sendSocketMessage()

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
        callMessageApi(userId.toString())

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
        mSocket.on("read", Emitter.Listener { args ->

            val data = args[0] as JSONObject
            try {

                runOnUiThread {
                    val readBy = data.getString("read_by")
                    val readTo = data.getString("read_to")
                    if (readTo == userId.toString() && readBy == myUserId) {
                        val isRead = data.getString("is_read")

                        for (i in 0 until chatList!!.size) {
                            if (isRead == 1.toString()) {
                                chatList!![i]!!.isTickRead=1
                            }else{
                                chatList!![i]!!.isTickRead=0
                            }
                        }
                        chattingAdapter!!.notifyDataSetChanged()
                    }

                }

            } catch (e: Exception) {

            }

        })
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
            }


        })
        isTyping()
        getIstypingUser()
        clearIsTyping()
        readUndreadMessage()
        /* chat1= Chats()
       myUserId =chat1!!.uid*/
    }

    private fun scrollToBottom() {
        runOnUiThread {
            //chattingAdapter!!.notifyDataSetChanged()
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
            jsonObject.put("is_image", "0")
            jsonObject.put("userType", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        mSocket = Chatimmi.getSocket()!!
        mSocket.emit("new_message", jsonObject)
        binding!!.etSendMessage.setText("")
    }

    private fun sendSocketImageMessage() {

    }

    fun callMessageApi(userId: String) {

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.getMessageApi(userId)
        callApi!!.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val gson = Gson().fromJson<Chat>(response.body().toString(), Chat::class.java)
                    Log.d("haxhskm", "onResponse: ${response.body()}")
                    chatList = gson.data?.messageData

                    //chatList?.reverse()
                    chattingAdapter = ChattingAdapter(this@ChatActivity, chatList, myUserId, { timestamp -> }, true)
                    binding!!.recyclerViews.adapter = chattingAdapter
                    scrollToBottom()


                } else {
                    Log.d("haxhskm", "onResponse: ${response.errorBody()}")

                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }

    val flag = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            userType
    )


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
                        jsonObject.put("frontUserID", userId)
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
            jsonObject.put("read_to", userId)
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
            typing.put("is_typing", 0)
            mSocket.emit("typing", typing)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getIstypingUser() {
        mSocket.on("typing") { args ->
            Log.e("typing", args[0].toString())
            val data = args[0] as JSONObject
            try {

                runOnUiThread {
                    val userID = data.getString("userID")

                    if (userID == userId.toString()) {
                        val typingStatus = data.getInt("is_typing")
                        if (typingStatus == 1) {
                            binding!!.appBar.tvShowTyping.visibility = View.VISIBLE
                            binding!!.appBar.tvShowTyping.text = "typing"
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
                showImage(uri!!)

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

    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item] == "Choose from Gallery") {
                val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhoto, 1)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
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