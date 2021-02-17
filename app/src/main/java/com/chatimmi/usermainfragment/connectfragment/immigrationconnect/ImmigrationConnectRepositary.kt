package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ImmigrationConnectRepositary(context: Context,var connectConsultentList: ApiCallback.ConnectConsultentList) {
    var session = com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    private val connectObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun getResponseDataConnectClick() = connectObserver as LiveData<UIStateManager>

    fun callConnectApi(deviceId: String, divicetoke: String, deviceType: String, deviceTimeZone: String, userId: String) {
        connectObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.setConsultantConnect("Bearer " + session.getAuthToken(), deviceId, divicetoke, deviceType, deviceTimeZone,
                userId)
        callApi?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    connectObserver.value = UIStateManager.Loading(false)
                    connectObserver.value = UIStateManager.Success(response.body())
                } else {
                    connectObserver.value = UIStateManager.Loading(false)
                    connectObserver.value = UIStateManager.Error(response.message())
                }
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                connectObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }

    fun callConsultantListApi(deviceId: String, divicetoke: String, deviceType: String, deviceTimeZone: String, userType: String) {
        groupApiObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.getConsultantList("Bearer " + session.getAuthToken(), deviceId, divicetoke, deviceType, deviceTimeZone,
                userType, "0", "1000")
        callApi?.enqueue(object : Callback<ConsultantListResponce> {
            override fun onResponse(call: Call<ConsultantListResponce>, response: Response<ConsultantListResponce>) {
                if (response.isSuccessful) {
                    groupApiObserver.value = UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    groupApiObserver.value = UIStateManager.Loading(false)
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    connectConsultentList.onError(gson.message.toString())
                    // groupApiObserver.value = UIStateManager.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ConsultantListResponce?>, t: Throwable) {
                groupApiObserver.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    connectConsultentList.onError("Please check your internet connections")
                } else {
                    connectConsultentList.onError("Something went wrong")
                }
                //groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }
}

