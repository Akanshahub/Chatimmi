package com.chatimmi.usermainfragment.otherfragment.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivitySettingBinding
import com.chatimmi.viewmodel.SettingViewModel

@Suppress("DEPRECATION")
class SettingActivity:BaseActivitykt(),CommonTaskPerformer {
    lateinit var binding:ActivitySettingBinding
    lateinit var viewModal: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_setting)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        viewModal = ViewModelProviders.of(this).get(SettingViewModel::class.java)
        binding.settingViewModel = viewModal
        binding.lifecycleOwner = this
        viewModal.init(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding.backButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                onBackPressed()

            }

        })
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {
        this.showToast(msg)
    }

    override fun dismissDialog() {

    }
}