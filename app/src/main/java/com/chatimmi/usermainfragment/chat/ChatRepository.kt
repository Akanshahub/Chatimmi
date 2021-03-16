package com.chatimmi.usermainfragment.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.model.ErrorResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ChatRepository(var context: Context)  {
    var session=com.chatimmi.app.pref.Session(context)
    private val groupApiObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    fun getResponseData() = groupApiObserver as LiveData<UIStateManager>
    fun callChatHistoryListApi() {

        groupApiObserver.value= UIStateManager.Loading(true)
            val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
            val callApi = api.callChatHistoryApi()
            callApi.enqueue(object : Callback<ChatHistoryResponse> {
                override fun onResponse(call: Call<ChatHistoryResponse>, response: Response<ChatHistoryResponse>) {
                    if (response.isSuccessful) {
                        groupApiObserver.value= UIStateManager.Loading(false)
                        groupApiObserver.value = UIStateManager.Success(response.body())
                    } else {
                        groupApiObserver.value= UIStateManager.Loading(false)
                        val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                       // groupFilterlist.onError(gson.message.toString())
                        //groupApiObserver.value = UIStateManager.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<ChatHistoryResponse?>, t: Throwable) {
                    // groupApiObserver.value = UIStateManager.Error(t.localizedMessage)
                    groupApiObserver.value= UIStateManager.Loading(false)
                 /*   if(t is IOException) {
                        groupFilterlist.onError(context.getString(R.string.no_network_connection))
                    }
                    else {
                        groupFilterlist.onError(context.getString(R.string.something_went_wrong))
                    }*/
                }
            })

    }




}