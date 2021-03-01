package com.chatimmi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.chatimmi.app.pref.Session
import com.chatimmi.fragmentchatimmi.ChatimmiActivity
import com.chatimmi.views.SignInActivity

@Suppress("DEPRECATION")
class SplashScreenActivity :  AppCompatActivity(){

    private val SPLASH_TIME_OUT = 2000
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        session = Session(this)

        val w = window
        w.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Handler().postDelayed({
            Log.d("kkk", "login: ${session.getIsUserLoggedIn()}")
            var sess = session.getIsUserLoggedIn()
            if (sess.equals("isLogin")) {
                val i = Intent(this, ChatimmiActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, SignInActivity::class.java)
                startActivity(i)
                finish()
            }

        }, SPLASH_TIME_OUT.toLong())


    }


}