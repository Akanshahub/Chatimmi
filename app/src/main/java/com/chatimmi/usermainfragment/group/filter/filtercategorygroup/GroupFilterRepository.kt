package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class GroupFilterRepository(context: Context) : ApiCallback.GroupFilterlist {
    var session=com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callGroupCategoryListApi(deviceId: String, divicetoke : String,deviceType: String, deviceTimeZone: String,  ) {
        groupApiObserver.value= UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.groupCategoryListApi(deviceId, divicetoke,deviceType,deviceTimeZone
                )
        callApi?.enqueue(object : Callback<GroupFilterResponse> {
            override fun onResponse(call: Call<GroupFilterResponse>, response: Response<GroupFilterResponse>) {
                if (response.isSuccessful) {
                    groupApiObserver.value= UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    groupApiObserver.value= UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Error(response.message())
                }
            }

            override fun onFailure(call: Call<GroupFilterResponse?>, t: Throwable) {
                groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }

    override fun onSuccessLogin(deliveryListResponse: GroupFilterResponse) {
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