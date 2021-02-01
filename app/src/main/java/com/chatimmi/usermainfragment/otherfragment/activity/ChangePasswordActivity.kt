package com.chatimmi.usermainfragment.otherfragment.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChangePasswordBinding
import com.chatimmi.viewmodel.ChangePassViewModel

@Suppress("DEPRECATION")
class ChangePasswordActivity : BaseActivitykt() {

    lateinit var binding: ActivityChangePasswordBinding
    lateinit var viewModel: ChangePassViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChangePassViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        binding.lifecycleOwner = this
        binding.changePassViewModel = viewModel
        binding.appBar.ivBack.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        viewModel.getValidationData().observe(this, Observer {
            when (it) {
                is UIStateManager.Error -> {
                    showToast(it.msg)
                }
                else -> {
                }
            }

        })

        viewModel.getUpdateData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        showToast("Under Development")
                    }
                    is UIStateManager.Error -> {
                        showToast(it.msg)
                    }
                    else -> {
                    }
                }
            }
        })
    }
}
