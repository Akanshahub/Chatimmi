package com.chatimmi.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
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
import java.io.IOException


class SignInRepository(var context: Context, var loginCallback: ApiCallback.LoginCallback) {
    var session=com.chatimmi.app.pref.Session(context)
    private val loginResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getLoginResponseData() = loginResponseObserver as LiveData<UIStateManager>
    private val fbResponseLiveData: MutableLiveData<UserDetialResponse>? = null

    fun callLoginApi(email: String, password: String, device_token: String, user_type: String) {
        loginResponseObserver.value=UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callLoginApi(email, password, user_type)
        callApi.enqueue(object : Callback<UserDetialResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {

                if (response.isSuccessful) {
                    loginResponseObserver.value=UIStateManager.Loading(false)
                    loginResponseObserver.value = UIStateManager.Success(response.body())

                } else {
                    loginResponseObserver.value=UIStateManager.Loading(false)
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    loginCallback.onError(gson.message.toString())
                   // loginResponseObserver.value = UIStateManager.Error(gson.message.toString())

                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                loginResponseObserver.value=UIStateManager.Loading(false)
                if(t is IOException) {
                    loginCallback.onError(context.getString(R.string.no_network_connection))
                }
                else {
                    loginCallback.onError(context.getString(R.string.something_went_wrong))
                }
            /*    t.localizedMessage.let {
                    loginResponseObserver.value = UIStateManager.Error(it)
                }*/


            }
        })
    }




}