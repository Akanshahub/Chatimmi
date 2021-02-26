package com.chatimmi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ContactUsResponse
import com.chatimmi.model.ErrorResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ContactUsRepository(var context: BaseActivitykt,var contactUsCallBack: ApiCallback.ContactUsCallBack) {
    var session=com.chatimmi.app.pref.Session(context)
    private val responseData by lazy {
        MutableLiveData<UIStateManager>()
    }
    fun getResponseData() = responseData as LiveData<UIStateManager>
    fun callContactUsApi(title: String,description: String) {
        responseData.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callContactUsApi(title,description)
        callApi.enqueue(object : Callback<ContactUsResponse> {
            override fun onResponse(call: Call<ContactUsResponse>, response: Response<ContactUsResponse>) {
                if (response.isSuccessful) {
                    responseData.value = UIStateManager.Loading(false)
                    responseData.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    responseData.value = UIStateManager.Loading(false)
                    contactUsCallBack.onError(gson.message.toString())

                }
            }

            override fun onFailure(call: Call<ContactUsResponse>, t: Throwable) {

                responseData.value = UIStateManager.Loading(false)
                if (t is IOException) {
                    contactUsCallBack.onError(context.getString(R.string.no_network_connection))
                } else {
                    contactUsCallBack.onError(context.getString(R.string.something_went_wrong))
                }
                /*   t.message?.let {
                        changePasswordResponseObserver.value = UIStateManager.Error(it)
                    }*/

            }
        })
    }

}