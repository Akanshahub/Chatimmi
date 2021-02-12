package com.chatimmi.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ChangePasswordResponse
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ChangePasswordRepository(var context: Context) {
    var session=com.chatimmi.app.pref.Session(context)
    private val changePasswordResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    private val logoutResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    private val setResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    fun getChangePasswordResponseData() = changePasswordResponseObserver as LiveData<UIStateManager>
    fun getSetPasswordResponseData() = setResponseObserver as LiveData<UIStateManager>

    fun callChangePasswordApi(deviceId: String, deviceToken: String, deviceType: String, deviceTimeZone: String, newPassword: String,oldPassword: String,confirmPassword: String) {
        changePasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callChangePasswordApi("Bearer "+session.getAuthToken(),deviceId,deviceToken,deviceType,deviceTimeZone,newPassword,oldPassword,confirmPassword)
        callApi.enqueue(object : Callback<ChangePasswordResponse> {

            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {

                    changePasswordResponseObserver.value = UIStateManager.Loading(false)
                    changePasswordResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    changePasswordResponseObserver.value = UIStateManager.Loading(false)
                    changePasswordResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                changePasswordResponseObserver.value = UIStateManager.Loading(false)
                t.message?.let {
                    changePasswordResponseObserver.value = UIStateManager.Error(it)
                }

            }
        })
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
                    val gson = Gson().fromJson(response.errorBody()?.string(), ChangePasswordResponse::class.java)
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
    fun callSetPasswordApi(deviceId: String, deviceToken: String, deviceType: String, deviceTimeZone: String, newPassword: String,confirmPassword: String) {
        changePasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callSetPasswordApi("Bearer "+session.getAuthToken(),deviceId,deviceToken,deviceType,deviceTimeZone,newPassword,confirmPassword)
        callApi.enqueue(object : Callback<ChangePasswordResponse> {

            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {

                    setResponseObserver.value = UIStateManager.Loading(false)
                    setResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    setResponseObserver.value = UIStateManager.Loading(false)
                    setResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                setResponseObserver.value = UIStateManager.Loading(false)
                t.message?.let {
                    setResponseObserver.value = UIStateManager.Error(it)
                }

            }
        })
    }

}