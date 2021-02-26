package com.chatimmi.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.model.ContentTermsConditionModel
import com.chatimmi.model.ErrorResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.retrofitnetwork.API
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.retrofitnetwork.RetrofitGenerator
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SignUpRepository(var context: BaseActivitykt, var signUpCallback: ApiCallback.SignUpCallback) {
    var session=com.chatimmi.app.pref.Session(context)
    private val signUpesponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }

    fun getSignUpResponseData() = signUpesponseObserver as LiveData<UIStateManager>


    fun SignUpCallback(fullName: RequestBody, email: RequestBody, password: RequestBody, user_type: RequestBody, confirm_password: RequestBody, profilePicture: MultipartBody.Part?) {
        signUpesponseObserver.value = UIStateManager.Loading(true)
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callsignupApi(fullName, email, password, user_type, confirm_password, profilePicture)
        callApi.enqueue(object : Callback<UserDetialResponse> {

            override fun onResponse(call: Call<UserDetialResponse>, response: Response<UserDetialResponse>) {
                if (response.isSuccessful) {
                    signUpesponseObserver.value = UIStateManager.Success(response.body())
                    signUpesponseObserver.value = UIStateManager.Loading(false)
                } else {
                    signUpesponseObserver.value = UIStateManager.Loading(false)
                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    signUpCallback.onError(gson.message.toString())
                    // signUpesponseObserver.value = UIStateManager.Error(gson.message.toString())

                }
            }

            override fun onFailure(call: Call<UserDetialResponse>, t: Throwable) {
                signUpesponseObserver.value = UIStateManager.Loading(false)

                if (t is IOException) {
                    signUpCallback.onError(context.getString(R.string.no_network_connection))
                } else {
                    signUpCallback.onError(context.getString(R.string.something_went_wrong))
                }
//                t.message?.let {
//                    signUpesponseObserver.value = UIStateManager.Error(it)
//                }


            }
        })
    }
    fun contentResponseObserver() = contentResponseObserver as LiveData<UIStateManager>
    private val contentResponseObserver by lazy {
        MutableLiveData<UIStateManager>()
    }
    fun callContentApi() {
        val api = RetrofitGenerator.getRetrofitObject().create(API::class.java)
        val callApi = api.callContentTermsConditionApi()
        callApi.enqueue(object : Callback<ContentTermsConditionModel> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<ContentTermsConditionModel>, response: Response<ContentTermsConditionModel>) {

                if(response.isSuccessful) {
                    contentResponseObserver.value = UIStateManager.Success(response.body())

                }
                else
                {

                    val gson = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    //  notificationSwitchCallBack.onError(gson.message.toString())
                }
            }

            override fun onFailure(call: Call<ContentTermsConditionModel>, t: Throwable) {
                if(t is IOException) {
                    //notificationSwitchCallBack.onError(context.getString(R.string.no_network_connection))
                }
                else {
                    // notificationSwitchCallBack.onError(context.getString(R.string.something_went_wrong))
                }
            }
        })
    }
}