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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.pref.Constants
import com.chatimmi.app.pref.PrefHelper
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.KeyboardUtils
import com.chatimmi.app.utils.ProgressDialog
import com.chatimmi.app.utils.StackSet
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.views.SignInActivity
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
open class BaseActivitykt : AppCompatActivity() {
    val backStack = StackSet<Fragment>()
    private val prefHelper: PrefHelper? = null
    var mSocket: Socket? = null

    private lateinit var session: Session
    private var progressDialog: ProgressDialog? = null
    val activity: Activity
        get() = this


    fun hideKeyboard() {
        if (currentFocus != null) KeyboardUtils.hideKeyboard(this, currentFocus!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)

        session = Session(this)
        //TODO Socket chat
       /* mSocket = Chatimmi().getSocket()
         SocketCont().getmSocket(mSocket!!,this)*/
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

    fun getLogOutResponseData() = logoutResponseObserver as LiveData<UIStateManager>
    private val logoutResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun callLogoutApi() {
        logoutResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callLogoutApi()
        callApi.enqueue(object : Callback<LogoutResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (response.isSuccessful) {
                    logoutResponseObserver.value = UIStateManager.Loading(false)
                    session.setIsUserLoggedIn("logout")
                    val intent = Intent(activity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    navigateTo(intent, true)
                    logoutResponseObserver.value = UIStateManager.Success(response.body())

                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    logoutResponseObserver.value = UIStateManager.Loading(false)
                    //logoutCallBack.onError(gson.message.toString())
                    //logoutResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {

                logoutResponseObserver.value = UIStateManager.Loading(false)
                /*   if (t is IOException) {
                       logoutCallBack.onError(context.getString(R.string.no_network_connection))
                   } else {
                       logoutCallBack.onError(context.getString(R.string.something_went_wrong))
                   }*/
                /*  t.localizedMessage.let {
                    logoutResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }
}