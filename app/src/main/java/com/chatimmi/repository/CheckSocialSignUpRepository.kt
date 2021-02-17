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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class CheckSocialSignUpRepository( var context: Context,var checkSocialSignupCallback: ApiCallback.CheckSocialSignupCallback) {

    fun callCheckSocialSignUpApi(deviceId: String,devicetoken: String,devicetype: String,timezone: String,socialid: String, socialtype: String,usertype: String) {
        checkSocialSignupCallback.onShowBaseLoader()

        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callCheckSocialSignupApi(deviceId, devicetoken,devicetype, timezone, socialid, socialtype,usertype)
        callApi.enqueue(object : Callback<UserDetialResponse> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                checkSocialSignupCallback.onHideBaseLoader()
                if(response.isSuccessful) {
                    checkSocialSignupCallback.onSuccessCheckSocialSignup(response.body()!!)

                }
                else
                {
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    checkSocialSignupCallback.onError(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                checkSocialSignupCallback.onHideBaseLoader()

                if(t is IOException) {
                    checkSocialSignupCallback.onError(context.getString(R.string.nointernet_connection_mssge))
                }
                else {
                    checkSocialSignupCallback.onError(context.getString(R.string.something_went_wrong))
                }
            }
        })
    }
}