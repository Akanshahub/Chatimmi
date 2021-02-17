package com.chatimmi.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chatimmi.Chatimmi
import com.chatimmi.app.pref.Constants
import com.chatimmi.app.pref.PrefHelper
import com.chatimmi.app.utils.KeyboardUtils
import com.chatimmi.app.utils.ProgressDialog
import com.chatimmi.app.utils.StackSet


import io.socket.client.Socket

@Suppress("DEPRECATION")
open class BaseActivitykt: AppCompatActivity() {
    val backStack = StackSet<Fragment>()
    private val prefHelper: PrefHelper? = null
    var mSocket: Socket? = null

    private var progressDialog: ProgressDialog? = null
    val activity: Activity
        get() = this


    fun hideKeyboard() {
        if (currentFocus != null) KeyboardUtils.hideKeyboard(this, currentFocus!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)



        //TODO Socket chat
      /*  mSocket = Chatimmi().getSocket()
        com.chatimmi.socketchat.SocketConstant().getmSocket(mSocket!!, this)*/

    }

    fun toastMessage(mssge: String?, context: Context?) {
        Toast.makeText(context, mssge, Toast.LENGTH_LONG).show()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor(color: Int) {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(color)
    }

    val currentFragment: Fragment?
        get() = backStack.peek()

    fun replaceFragment(fragment: Fragment, @IdRes containerId: Int, addToBackStack: Boolean) {
        try {
            val fm = supportFragmentManager
            val fragmentName = fragment.javaClass.name
            fm.beginTransaction().replace(containerId, fragment, fragmentName).commit()
            if (addToBackStack) backStack.push(fragment)
            hideKeyboard()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun <T> navigateTo(destination: Class<T>?, bundle: Bundle?) {
        val intent = Intent(this, destination)
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle)
        }
        startActivity(intent)
    }

    fun <T> navigateTo(destination: Class<T>?, bundle: Bundle?, isFinishing: Boolean) {
        val intent = Intent(this, destination)
        if (bundle != null) {
            intent.putExtra(Constants.KEY_BUNDLE_PARAM, bundle)
        }
        startActivity(intent)
        if (isFinishing) finish()
    }
    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
    fun navigateTo(intent: Intent?, isFinishing: Boolean) {
        startActivity(intent)
        if (isFinishing) finish()
    }

    fun hideLoader() {
        progressDialog!!.dismiss()
    }

    fun showLoader() {
        if (!isFinishing) {
            progressDialog!!.show()
        }
    }

}