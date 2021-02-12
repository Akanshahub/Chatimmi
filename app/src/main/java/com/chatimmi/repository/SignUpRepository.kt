package com.chatimmi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.app.utils.UIStateManager
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

class SignUpRepository {
    private val signUpesponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getSignUpResponseData() = signUpesponseObserver as LiveData<UIStateManager>


    fun SignUpCallback(devicetoken: String, device_id: String, device_type: String, timezone: String, fullName: RequestBody, email: RequestBody, password: RequestBody, user_type: RequestBody, confirm_password: RequestBody, profilePicture: MultipartBody.Part?) {
        signUpesponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callsignupApi(devicetoken, device_id, device_type, timezone, fullName, email, password, user_type, confirm_password, profilePicture)
        callApi.enqueue(object : Callback<UserDetialResponse> {

            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                if (response.isSuccessful) {
                    signUpesponseObserver.value = UIStateManager.Success(response.body())
                    signUpesponseObserver.value = UIStateManager.Loading(false)
                } else {
                    signUpesponseObserver.value = UIStateManager.Loading(false)
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    signUpesponseObserver.value = UIStateManager.Error(gson.message.toString())

                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                signUpesponseObserver.value = UIStateManager.Loading(false)
                t.message?.let {
                    signUpesponseObserver.value = UIStateManager.Error(it)
                }


            }
        })
    }
}