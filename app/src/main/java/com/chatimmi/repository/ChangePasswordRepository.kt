package com.chatimmi.repository

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ChangePasswordResponse
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


class ChangePasswordRepository(var context: BaseActivitykt, var changePasswordCallBack: ApiCallback.ChangePasswordCallBack,var logoutCallBack: ApiCallback.LogoutCallback,var setPasswordCallback: ApiCallback.SetPasswordCallBack) {
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

    fun callChangePasswordApi(newPassword: String,oldPassword: String,confirmPassword: String) {
        changePasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callChangePasswordApi(newPassword,oldPassword,confirmPassword)
        callApi.enqueue(object : Callback<ChangePasswordResponse> {

            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    changePasswordResponseObserver.value = UIStateManager.Loading(false)
                    changePasswordResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    changePasswordResponseObserver.value = UIStateManager.Loading(false)
                    changePasswordCallBack.onError(gson.message.toString())
                //changePasswordResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                changePasswordResponseObserver.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    changePasswordCallBack.onError(context.getString(R.string.no_network_connection))
                } else {
                    changePasswordCallBack.onError(context.getString(R.string.something_went_wrong))
                }
            /*   t.message?.let {
                    changePasswordResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }

    fun getLogOutResponseData() = logoutResponseObserver as LiveData<UIStateManager>
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
                    logoutCallBack.onError(context.getString(R.string.no_network_connection))
                } else {
                    logoutCallBack.onError(context.getString(R.string.something_went_wrong))
                }
                /*  t.localizedMessage.let {
                    logoutResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }
    fun callSetPasswordApi(newPassword: String,confirmPassword: String) {
        changePasswordResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callSetPasswordApi(newPassword,confirmPassword)
        callApi.enqueue(object : Callback<ChangePasswordResponse> {

            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    setResponseObserver.value = UIStateManager.Loading(false)
                    setResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    setResponseObserver.value=UIStateManager.Loading(false)
                    if(response.code() == 401){
                        setResponseObserver.value = UIStateManager.ErrorCode(response.code())
                    }else{
                        setPasswordCallback.onError(response.message())
                    }

                }

            /*else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    setResponseObserver.value = UIStateManager.Loading(false)
                    setPasswordCallback.onError(gson.message.toString())
                   // setResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }*/
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {

                setResponseObserver.value = UIStateManager.Loading(false)

                if (t is IOException) {
                    setPasswordCallback.onError(context.getString(R.string.no_network_connection))
                } else {
                    setPasswordCallback.onError(context.getString(R.string.something_went_wrong))
                }
         /*       t.message?.let {
                    setResponseObserver.value = UIStateManager.Error(it)
                }*/

            }
        })
    }

}