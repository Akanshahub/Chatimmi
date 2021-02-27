package com.chatimmi.repository

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ContentTermsConditionModel
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.views.SignInActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LogoutRepository(var context: BaseActivitykt, var logoutCallBack: ApiCallback.LogoutCallback,var notificationSwitchCallBack: ApiCallback.NotificationSwitchCallBack) {
    var session=com.chatimmi.app.pref.Session(context)

    fun getLogOutResponseData() = logoutResponseObserver as LiveData<UIStateManager>
    private val logoutResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }


    fun ResponseObserver() = ResponseObserver as LiveData<UIStateManager>
    private val ResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun contentResponseObserver() = contentResponseObserver as LiveData<UIStateManager>
    private val contentResponseObserver by lazy {
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
                    val intent = Intent(context, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

        fun callNotificationSwitchApi(flag: String) {
            ResponseObserver.value = UIStateManager.Loading(true)
            val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
            val callApi = api.callNotificationSwitchApi(flag)
            callApi.enqueue(object : Callback<UserDetialResponse> {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                    ResponseObserver.value = UIStateManager.Loading(false)
                    if(response.isSuccessful) {
                        ResponseObserver.value = UIStateManager.Success(response.body())

                    }
                    else
                    {
                        ResponseObserver.value = UIStateManager.Loading(false)
                        val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        notificationSwitchCallBack.onError(gson.message.toString())
                    }
                }

                override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                    if(t is IOException) {
                        notificationSwitchCallBack.onError(context.getString(R.string.no_network_connection))
                    }
                    else {
                        notificationSwitchCallBack.onError(context.getString(R.string.something_went_wrong))
                    }
                }
            })
        }

           fun callContentApi() {
               val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
            val callApi = api.callContentTermsConditionApi()
            callApi.enqueue(object : Callback<ContentTermsConditionModel> {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onResponse(call: Call<ContentTermsConditionModel>, response: Response<ContentTermsConditionModel>) {

                    if(response.isSuccessful) {
                        contentResponseObserver.value = UIStateManager.Success(response.body())

                    }
                    else
                    {

                        val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                      //  notificationSwitchCallBack.onError(gson.message.toString())
                    }
                }

                override fun onFailure(call: Call<ContentTermsConditionModel>, t: Throwable) {
                    if(t is IOException) {
                        //notificationSwitchCallBack.onError(context.getString(R.string.no_network_connection))
                    }
                    else {
                       // notificationSwitchCallBack.onError(context.getString(R.string.something_went_wrong))
                    }
                }
            })
        }
    }