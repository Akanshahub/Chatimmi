package com.chatimmi.fragmentchatimmi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityChatImmiBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ChatimmiActivity : BaseActivitykt() {
    private var binding: ActivityChatImmiBinding? = null
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_immi)
      /*mSocket = Chatimmi().getInstance()!!.getSocket()
        com.chatimmi.socketchat.SocketConstant().getInstance()!!.getmSocket(mSocket!!, this)*/
       // mSocket!!.connect()
      //  mSocket.Chatimmi().get
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        setUpBottomNav(navController)
    }
    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}
