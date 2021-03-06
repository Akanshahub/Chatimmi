package com.chatimmi.base


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateUtils
import android.view.View
import android.view.WindowManager
import android.widget.TextView
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
open class BaseActivitykt : AppCompatActivity() {
    val backStack = StackSet<Fragment>()
    private val prefHelper: PrefHelper? = null


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
         SocketCont.getInstance().getmSocket(mSocket, this)*/


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
                    Toast.makeText(activity, "Session expired please login again", Toast.LENGTH_LONG).show()
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

    fun getYesterdayDateV2(dateString: String): String {
        var day = ""

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateString)
        df.timeZone = TimeZone.getDefault()

        var isTodayLong = date.time
        var tBoolean = DateUtils.isToday(isTodayLong)

        val sim = SimpleDateFormat("MMMM dd, yyyy HH:mm aa", Locale.US)
        var dayBeforeYesterday = sim.format(date)
        val sim1 = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        var dayBeforeYesterdayString = sim1.format(date)

        val sdf = SimpleDateFormat("HH:mm aa", Locale.US)
        val currentDateandTime = sdf.format(date)

        val sdf1 = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val currentDateandTime1 = sdf1.format(date)

        val date11 = sdf.parse(currentDateandTime)
        val calender = Calendar.getInstance()
        calender.time = date11

        if (tBoolean) {
            if (currentDateandTime.substring(currentDateandTime.length - 2).startsWith("A")) {
                // AM
                val d1 = get12HourFormatV2(date, " AM")
                day = "$d1 "
            } else {
                // PM
                val d2 = get12HourFormatV2(date, " PM")
                day = "$d2 "
            }
        } else {
            if (currentDateandTime1.equals(getYesterdayDateString())) {
                if (currentDateandTime.substring(currentDateandTime.length - 2).startsWith("A")) {
                    // AM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    day = "Yesterday "
                } else {
                    // PM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    day = "Yesterday "
                }
            } else {
                if (dayBeforeYesterday.substring(dayBeforeYesterday.length - 2).startsWith("A")) {
                    // AM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    day = dayBeforeYesterdayString.toUpperCase()
                } else {
                    // PM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    day = dayBeforeYesterdayString.toUpperCase()
                }
                //textView.text = dayBeforeYesterday
            }
        }
        return day
    }

    fun getYesterdayDate(dateString: String, textView: TextView) {

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(dateString)
        df.timeZone = TimeZone.getDefault()

        var isTodayLong = date.time
        var tBoolean = DateUtils.isToday(isTodayLong)

        val sim = SimpleDateFormat("MMMM dd, yyyy HH:mm aa", Locale.US)
        var dayBeforeYesterday = sim.format(date)
        val sim1 = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
        var dayBeforeYesterdayString = sim1.format(date)

        val sdf = SimpleDateFormat("HH:mm aa", Locale.US)
        val currentDateandTime = sdf.format(date)

        val sdf1 = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val currentDateandTime1 = sdf1.format(date)

        val date11 = sdf.parse(currentDateandTime)
        val calender = Calendar.getInstance()
        calender.time = date11

        if (tBoolean) {
            if (currentDateandTime.substring(currentDateandTime.length - 2).startsWith("A")) {
                // AM
                get12HourFormat(date, textView, " AM")

            } else {
                // PM
                get12HourFormat(date, textView, " PM")
            }
        } else {
            if (currentDateandTime1.equals(getYesterdayDateString())) {
                if (currentDateandTime.substring(currentDateandTime.length - 2).startsWith("A")) {
                    // AM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    textView.text = "Yesterday "
                } else {
                    // PM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    textView.text = "Yesterday "
                }
            } else {
                if (dayBeforeYesterday.substring(dayBeforeYesterday.length - 2).startsWith("A")) {
                    // AM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    textView.text = dayBeforeYesterdayString.toUpperCase()
                } else {
                    // PM
                    val time1 = SimpleDateFormat("HH:mm")
                    val strTime1 = time1.format(date)
                    val _12HourSDF = SimpleDateFormat("hh:mm")
                    val _24HourDt = time1.parse(strTime1)
                    textView.text = dayBeforeYesterdayString.toUpperCase()
                }
                //textView.text = dayBeforeYesterday
            }
        }

    }

    fun get12HourFormat(date: Date, textView: TextView, str: String) {
        val time1 = SimpleDateFormat("HH:mm")
        val strTime1 = time1.format(date)
        val _12HourSDF = SimpleDateFormat("hh:mm")
        val _24HourDt = time1.parse(strTime1)
        textView.text = "Today "
    }

    fun get12HourFormatV2(date: Date, str: String): String {
        val time1 = SimpleDateFormat("HH:mm")
        val strTime1 = time1.format(date)
        val _12HourSDF = SimpleDateFormat("hh:mm")
        val _24HourDt = time1.parse(strTime1)
        return "Today "
    }

    fun getYesterdayDateString(): String {
        var dateFormat = SimpleDateFormat("dd/MM/yyyy");
        var cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time)
    }

    fun getYesterdayDateStringV2(): String {
        var dateFormat = SimpleDateFormat("dd/MM/yyyy");
        var cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time)
    }
}