package com.chatimmi.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.chatimmi.Chatimmi
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

    fun callSocialSignUpApi(deviceId:String,devicetoken:String,devicetype: String,timezone: String,fullName: String,email: String,userType: String,socialId: String,socialType: String,profileImage: String) {
        socialSignupCallback.onShowBaseLoader()

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callSocialSignupApi(deviceId,devicetoken,devicetype,timezone,fullName,email,userType,socialId,socialType,profileImage)

        Log.e("INFORMATION ", deviceId+"   "+devicetoken+"   "+timezone+"   "+fullName+"   "+email+"   "
                +"   "+socialId+"   "+socialType+"   "+profileImage+"   "+socialType+"   "+devicetype+"   "
                +userType  )

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
                    socialSignupCallback.onError(context.getString(R.string.nointernet_connection_mssge))
                }
                else {
                    socialSignupCallback.onError(context.getString(R.string.something_went_wrong))
                }
            }
        })
    }
}