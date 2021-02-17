package com.chatimmi.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ForgetPasswordRepository(var resetPasswordCallback: ApiCallback.ResetPasswordCallback) {
    private val resetPasswordResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResetPasswordResponseData() = resetPasswordResponseObserver as LiveData<UIStateManager>

    fun callResetPasswordApi(deviceId: String, devicetoken: String, deviceType: String, deviceTimeZone: String, email: String) {
        resetPasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callResetPasswordApi(deviceId, devicetoken, deviceType, deviceTimeZone, email)
        callApi.enqueue(object : Callback<ResetPasswordResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                if (response.isSuccessful) {
                    resetPasswordResponseObserver.value = UIStateManager.Loading(false)
                    resetPasswordResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    resetPasswordResponseObserver.value = UIStateManager.Loading(false)
                    resetPasswordCallback.onError(gson.message.toString())
                    //resetPasswordResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {

                resetPasswordResponseObserver.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    resetPasswordCallback.onError("Please check your internet connections")
                } else {
                    resetPasswordCallback.onError("Something went wrong")
                }
            /* t.message?.let {
                    resetPasswordResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }




}