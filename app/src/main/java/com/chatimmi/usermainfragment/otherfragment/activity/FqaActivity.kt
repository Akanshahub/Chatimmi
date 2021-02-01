package com.chatimmi.usermainfragment.otherfragment.activity

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.databinding.ActivityContactUsBinding
import com.chatimmi.databinding.ActivityFaqBinding


@Suppress("DEPRECATION")
class FqaActivity : AppCompatActivity() {
    lateinit var binding: ActivityFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_contact_us)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)

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
}