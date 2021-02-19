package com.chatimmi.usermainfragment.group.immigration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ImmigrationGroupRepositary(var context: Context,var grouplist :ApiCallback.grouplist)   {
var session=com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callGroupListApi(grouptype: String,category: String,subcategory: String,groupscope: String) {
        groupApiObserver.value=UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.groupListApi(grouptype,subcategory,category,groupscope)
        callApi?.enqueue(object : Callback<GroupListResponse> {
            override fun onResponse(call: Call<GroupListResponse>, response: Response<GroupListResponse>) {
                if (response.isSuccessful) {
                    groupApiObserver.value=UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    groupApiObserver.value=UIStateManager.Loading(false)
                   // groupApiObserver.value = UIStateManager.Error(response.message())
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    grouplist.onError(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<GroupListResponse?>, t: Throwable) {
                groupApiObserver.value=UIStateManager.Loading(false)

                if(t is IOException) {
                    grouplist.onError(context.getString(R.string.no_network_connection))
                }
                else {
                    grouplist.onError(context.getString(R.string.something_went_wrong))
                }
               // groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }




}

