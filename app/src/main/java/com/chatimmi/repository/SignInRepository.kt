package com.chatimmi.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInRepository  {
    private val loginResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getLoginResponseData() = loginResponseObserver as LiveData<UIStateManager>
    private val fbResponseLiveData: MutableLiveData<UserDetialResponse>? = null

    fun callLoginApi(deviceId: String, deviceType: String, deviceTimeZone: String, email: String, password: String, device_token: String, user_type: String) {
        loginResponseObserver.value=UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callLoginApi(deviceId, deviceType, deviceTimeZone, email, password, user_type, device_token)
        callApi.enqueue(object : Callback<UserDetialResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {

                if (response.isSuccessful) {
                    loginResponseObserver.value=UIStateManager.Loading(false)
                    loginResponseObserver.value = UIStateManager.Success(response.body())

                } else {
                    loginResponseObserver.value=UIStateManager.Loading(false)
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    loginResponseObserver.value = UIStateManager.Error(gson.message.toString())

                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                loginResponseObserver.value=UIStateManager.Loading(false)
                t.localizedMessage.let {
                    loginResponseObserver.value = UIStateManager.Error(it)
                }


            }
        })
    }




}