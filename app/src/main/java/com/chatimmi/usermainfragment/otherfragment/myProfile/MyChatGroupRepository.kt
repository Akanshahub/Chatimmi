package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MyChatGroupRepository(var context: Context)   {
    var session=com.chatimmi.app.pref.Session(context)

    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callGroupListApi(type: String,group_type: String) {
        groupApiObserver.value= UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.getProfileChatGroupApi(type,group_type,"0", "1000")
        callApi.enqueue(object : Callback<GetProfileChatGroupResponse> {
            override fun onResponse(call: Call<GetProfileChatGroupResponse>, response: Response<GetProfileChatGroupResponse>) {
                if (response.isSuccessful) {
                    groupApiObserver.value= UIStateManager.Loading(false)
                    groupApiObserver.value = UIStateManager.Success(response.body())
                } else {
                    groupApiObserver.value= UIStateManager.Loading(false)
                    // groupApiObserver.value = UIStateManager.Error(response.message())
                    // val gson = Gson().fromJson(response.errorBody()?.ob, ErrorResponse::class.java)

                }
            }

            override fun onFailure(call: Call<GetProfileChatGroupResponse?>, t: Throwable) {
                groupApiObserver.value= UIStateManager.Loading(false)

              /*  if(t is IOException) {
                    grouplist.onError(context.getString(R.string.no_network_connection))
                }
                else {
                    grouplist.onError(context.getString(R.string.something_went_wrong))
                }*/
                // groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
            }
        })
    }




}