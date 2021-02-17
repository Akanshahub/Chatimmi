package com.chatimmi.repository

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.views.SignInActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LogoutRepository(var context: BaseActivitykt, var logoutCallBack: ApiCallback.LogoutCallback) {
    var session=com.chatimmi.app.pref.Session(context)

    private val logoutResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getLogOutResponseData() = logoutResponseObserver as LiveData<UIStateManager>
    fun callLogoutApi(deviceId: String, devicetoken: String, deviceType: String, deviceTimeZone: String) {
        logoutResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callLogoutApi(deviceId, devicetoken, deviceType, deviceTimeZone)
        callApi.enqueue(object : Callback<LogoutResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (response.isSuccessful) {
                    logoutResponseObserver.value = UIStateManager.Loading(false)
                    session.setIsUserLoggedIn("logout")
                    val intent = Intent(context, SignInActivity::class.java)
                    context.navigateTo(intent, true)
                    logoutResponseObserver.value = UIStateManager.Success(response.body())

                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    logoutResponseObserver.value = UIStateManager.Loading(false)
                    logoutCallBack.onError(gson.message.toString())
                    //logoutResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {

                logoutResponseObserver.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    logoutCallBack.onError("Please check your internet connections")
                } else {
                    logoutCallBack.onError("Something went wrong")
                }
                /*  t.localizedMessage.let {
                    logoutResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }
}