package com.chatimmi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class EditProfileRepository(var context: BaseActivitykt) {
    var session = com.chatimmi.app.pref.Session(context)
    private val editProfileResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }


    fun editProfileResponseData() = editProfileResponseObserver as LiveData<UIStateManager>

    fun calleditProfileApi(fullName: RequestBody, profilePicture: MultipartBody.Part?) {
        editProfileResponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.updateProfileApi(fullName,profilePicture)
        callApi.enqueue(object : Callback<UserDetialResponse> {

            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                if (response.isSuccessful) {
                    editProfileResponseObserver.value = UIStateManager.Loading(false)
                    editProfileResponseObserver.value = UIStateManager.Success(response.body())
                } else {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    editProfileResponseObserver.value = UIStateManager.Loading(false)
                    //editProfileResponseObserver.onError(gson.message.toString())
                    //changePasswordResponseObserver.value = UIStateManager.Error(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {

                editProfileResponseObserver.value = UIStateManager.Loading(false)
               /* if (t is IOException) {
                    changePasswordCallBack.onError(context.getString(R.string.no_network_connection))
                } else {
                    changePasswordCallBack.onError(context.getString(R.string.something_went_wrong))
                }*/
                /*   t.message?.let {
                        changePasswordResponseObserver.value = UIStateManager.Error(it)
                    }*/

            }
        })
    }
}