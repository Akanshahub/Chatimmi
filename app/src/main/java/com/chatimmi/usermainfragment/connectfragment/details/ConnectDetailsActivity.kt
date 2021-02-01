package com.chatimmi.usermainfragment.connectfragment.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityConnectDetailsBinding
import com.chatimmi.helper.cateagoriesdailog.CateagoriesConnectBottomDailog
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

@Suppress("DEPRECATION")
class ConnectDetailsActivity : BaseActivitykt() {
    private var binding: ActivityConnectDetailsBinding? = null
    private var viewModel: ConnectDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_connect_details)
        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setupBindings(savedInstanceState)
    }
    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ConnectDetailsViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        binding!!.connectModel = viewModel
        binding!!.ivBtnBack.setOnClickListener {
            onBackPressed()
        }
        binding!!.tvViewMore.setOnClickListener(){

            val cateagoriesConnectBottomDailog=CateagoriesConnectBottomDailog()
            cateagoriesConnectBottomDailog.isCancelable=false
            cateagoriesConnectBottomDailog.show(supportFragmentManager)

        }
    }
}


