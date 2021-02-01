package com.chatimmi.usermainfragment.otherfragment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.databinding.ActivityContactUsBinding


class ContactUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_contact_us)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)
        binding.appBar.ivBtnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                onBackPressed()

            }

        })
    }
}