package com.chatimmi.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.chatimmi.R
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException



class socialSignUpRepository(var context: Context, var socialSignupCallback: ApiCallback.SocialSignupCallback) {

    fun callSocialSignUpApi(fullName: String,email: String,userType: String,socialId: String,socialType: String,profileImage: String) {
        socialSignupCallback.onShowBaseLoader()

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callSocialSignupApi(fullName,email,userType,socialId,socialType,profileImage)


        callApi.enqueue(object : Callback<UserDetialResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                socialSignupCallback.onHideBaseLoader()
                try {
                    if(response.isSuccessful) {
                        socialSignupCallback.onSuccessSocialSignup(response.body()!!)

                    }
                    else
                    { val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        socialSignupCallback.onError(gson.message.toString())
                    }
                }
                catch (e: Exception) {
                   // socialSignupCallback.onError(context.getString(R.string.something_went_wrong))
                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                socialSignupCallback.onHideBaseLoader()
                if(t is IOException) {
                    socialSignupCallback.onError(context.getString(R.string.no_network_connection))
                }
                else {
                    socialSignupCallback.onError(context.getString(R.string.something_went_wrong))
                }
            }
        })
    }
}