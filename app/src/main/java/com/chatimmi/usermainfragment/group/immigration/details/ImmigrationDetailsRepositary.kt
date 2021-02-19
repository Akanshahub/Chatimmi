package com.chatimmi.usermainfragment.group.immigration.details

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.helper.joindailong.JoinGroupResponse
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


class ImmigrationDetailsRepositary(var context: Context,var immigrationDetailsCallBack: ApiCallback.ImmigrationDetailsCallBack)  {
    var session=com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    private val connectObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callGroupDetailResponse(groupId: String) {
        groupApiObserver.value= UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.groupDetailApi(groupId)

        callApi?.enqueue(object : Callback<ImmigrationDetailsResponse> {
            override fun onResponse(call: Call<ImmigrationDetailsResponse>, response: Response<ImmigrationDetailsResponse>) {
                if (response.isSuccessful) {
                    groupApiObserver.value= UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    groupApiObserver.value= UIStateManager.Loading(false)
                    immigrationDetailsCallBack.onError(gson.message.toString())
                    //groupApiObserver.value = UIStateManager.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ImmigrationDetailsResponse?>, t: Throwable) {
                groupApiObserver.value=UIStateManager.Loading(false)
                if (t is IOException) {
                    immigrationDetailsCallBack.onError(context.getString(R.string.no_network_connection))
                } else {
                    immigrationDetailsCallBack.onError(context.getString(R.string.something_went_wrong))
                }
             /*   t.localizedMessage.let {
                    groupApiObserver.value = UIStateManager.Error(it!!)
                }*/

            }
        })
    }



    private val connectGroupResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getConnectGroupResponseData() = connectGroupResponseObserver as LiveData<UIStateManager>

    fun callConnectGroupApi(groupId: String) {
        connectGroupResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callConnectGroupApi(groupId)
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

    fun getResponseDataConnectClick() = connectObserver as LiveData<UIStateManager>

    fun callConnectApi(userId:String) {
        connectObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.setConsultantConnect(userId)
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
}