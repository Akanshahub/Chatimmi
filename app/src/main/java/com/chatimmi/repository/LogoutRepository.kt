package com.chatimmi.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutRepository {
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
                    logoutResponseObserver.value = UIStateManager.Success(response.body())

                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    logoutResponseObserver.value = UIStateManager.Loading(false)
                    logoutResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {

                logoutResponseObserver.value = UIStateManager.Loading(false)
                t.localizedMessage.let {
                    logoutResponseObserver.value = UIStateManager.Error(it)
                }

            }
        })
    }
}