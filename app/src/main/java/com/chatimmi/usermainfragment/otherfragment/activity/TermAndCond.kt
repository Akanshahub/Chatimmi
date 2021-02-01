package com.chatimmi.usermainfragment.otherfragment.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityTermAndCondBinding


@Suppress("DEPRECATION")
class TermAndCond : BaseActivitykt() {
    private var binding: ActivityTermAndCondBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_term_and_cond)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding!!.backButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onBackPressed()
                /* val intent = Intent(this@TermAndCond, SignupActivitykt::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                 startActivity(intent)*/


            }
        })
    }
}