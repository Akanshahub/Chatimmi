package com.chatimmi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.chatimmi.views.SignInActivity
import com.chatimmi.base.BaseActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : BaseActivity() {

    private val SPLASH_TIME_OUT = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Handler().postDelayed({

            val i = Intent(this, SignInActivity::class.java)
            startActivity(i)
            finish()

        }, SPLASH_TIME_OUT.toLong())
    }


}