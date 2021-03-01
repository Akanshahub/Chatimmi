package com.chatimmi.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ForgetPasswordRepository(var context: Context,var resetPasswordCallback: ApiCallback.ResetPasswordCallback) {
    var session=com.chatimmi.app.pref.Session(context)
    private val resetPasswordResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResetPasswordResponseData() = resetPasswordResponseObserver as LiveData<UIStateManager>

    fun callResetPasswordApi(email: String) {
        resetPasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callResetPasswordApi(email)
        callApi.enqueue(object : Callback<ResetPasswordResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                if (response.isSuccessful) {
                    resetPasswordResponseObserver.value = UIStateManager.Loading(false)
                    resetPasswordResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    resetPasswordResponseObserver.value=UIStateManager.Loading(false)
                    if(response.code() == 401){
                        resetPasswordResponseObserver.value = UIStateManager.ErrorCode(response.code())
                    }else{
                        resetPasswordCallback.onError(response.message())
                    }

                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {

                resetPasswordResponseObserver.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    resetPasswordCallback.onError(context.getString(R.string.no_network_connection))
                } else {
                    resetPasswordCallback.onError(context.getString(R.string.something_went_wrong))
                }
            /* t.message?.let {
                    resetPasswordResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }




}