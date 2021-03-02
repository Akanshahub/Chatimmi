package com.chatimmi.usermainfragment.activity.notification

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityNotificationBinding
import io.socket.client.Socket

class NotificationActivity : BaseActivity() {
    private var viewModel: NotificationViewModel? = null
    lateinit var binding: ActivityNotificationBinding
    var mSocket: Socket? = null
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        setupBindings(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //TODO Socket chat
        /*mSocket =Chatimmi.getSocket()
        SocketConstant.getInstance().getmSocket(mSocket, this)*/


       // SocketCont().getmSocket(SocketCont.getSocketInstance(), this)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[NotificationViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        binding.notificationModel = viewModel
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect();
    }
}