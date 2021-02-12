package com.chatimmi.helper.joindailong

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class JoinRespository(context: Context) {
    var session=com.chatimmi.app.pref.Session(context)

    private val connectGroupResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getConnectGroupResponseData() = connectGroupResponseObserver as LiveData<UIStateManager>

    fun callConnectGroupApi( deviceId: String,deviceToken: String,deviceType: String,timezone: String,groupId: String) {
        connectGroupResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callConnectGroupApi("Bearer "+session.getAuthToken(),deviceId,deviceToken,deviceType,timezone,groupId)
        callApi.enqueue(object : Callback<JoinGroupResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<JoinGroupResponse>, response: Response<JoinGroupResponse>) {
                if (response.isSuccessful) {

                    connectGroupResponseObserver.value = UIStateManager.Loading(false)
                    connectGroupResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    connectGroupResponseObserver.value = UIStateManager.Loading(false)
                    connectGroupResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<JoinGroupResponse>, t: Throwable) {

                connectGroupResponseObserver.value = UIStateManager.Loading(false)
                t.message?.let {
                    connectGroupResponseObserver.value = UIStateManager.Error(it)
                }

            }
        })
    }




}