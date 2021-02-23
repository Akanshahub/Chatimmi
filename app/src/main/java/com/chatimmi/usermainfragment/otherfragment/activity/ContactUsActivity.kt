package com.chatimmi.usermainfragment.otherfragment.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityContactUsBinding


class ContactUsActivity : BaseActivitykt() {
    lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_contact_us)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        binding.appBar.ivBtnBack.setOnClickListener { onBackPressed() }
    }
}