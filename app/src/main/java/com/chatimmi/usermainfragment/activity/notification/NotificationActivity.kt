package com.chatimmi.usermainfragment.activity.notification

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {
    private var viewModel: NotificationViewModel? = null
    lateinit var binding: ActivityNotificationBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        setupBindings(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
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
}