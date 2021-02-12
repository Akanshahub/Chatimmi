package com.chatimmi.usermainfragment.group.immigration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ImmigrationGroupRepositary(context: Context) : ApiCallback.grouplist {
var session=com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callGroupListApi(deviceId: String, divicetoke : String,deviceType: String, deviceTimeZone: String,  grouptype: String,category: String,subcategory: String,groupscope: String) {
        groupApiObserver.value=UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.groupListApi("Bearer "+session.getAuthToken(),deviceId, divicetoke,deviceType, deviceTimeZone,
                grouptype,subcategory,category,groupscope)
        callApi?.enqueue(object : Callback<GroupListResponse> {
            override fun onResponse(call: Call<GroupListResponse>, response: Response<GroupListResponse>) {
                if (response.isSuccessful) {
                    groupApiObserver.value=UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    groupApiObserver.value=UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Error(response.message())
                }
            }

            override fun onFailure(call: Call<GroupListResponse?>, t: Throwable) {
                groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }

    override fun onSuccessLogin(deliveryListResponse: GroupListResponse) {
        TODO("Not yet implemented")
    }

    override fun onShowBaseLoader() {
        TODO("Not yet implemented")
    }

    override fun onHideBaseLoader() {
        TODO("Not yet implemented")
    }

    override fun onError(errorMessage: String) {
        TODO("Not yet implemented")
    }


}

