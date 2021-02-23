package com.chatimmi.app.pref

import android.content.Context
import android.content.SharedPreferences
import com.chatimmi.model.UserDetialResponse
import com.google.gson.Gson

class Session(context: Context) {
    private  var mypref: SharedPreferences
    private  var editor: SharedPreferences.Editor
    private val IsUserLoggedIn = "userLoggedIn"
    private var PrefName = "CHATIMMI"
    private val AuthToken = "authToken"
    private val NotificationStatus = "notificationStatus"

    init {
        mypref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        editor = mypref.edit()
        editor.apply()
    }

    fun setIsUserLoggedIn(logiIn: String) {
        editor.putString(IsUserLoggedIn, logiIn)
        editor.commit()
    }

    fun getIsUserLoggedIn(): String {
        return mypref.getString(IsUserLoggedIn, "yes").toString()
    }

    fun setUserData(loginRegistrationResponse: UserDetialResponse) {

        val gson = Gson()
        val userStr = gson.toJson(loginRegistrationResponse)
        editor.putString(PrefName, userStr)
        editor.putString(AuthToken, loginRegistrationResponse.data?.user_details?.token)
        editor.commit()

    }

    fun getUserData(): UserDetialResponse? {
        val gson = Gson()
        val str = mypref.getString(PrefName, "")
        if (!str!!.isEmpty())
            return gson.fromJson(str, UserDetialResponse::class.java)
        else
            return null
    }

    fun getAuthToken(): String {
        return mypref.getString(AuthToken, "").toString()
    }
    fun setNotificationStatus(status: String) {
        editor.putString(NotificationStatus, status)
        editor.apply()
    }

    fun getNotificationStatus(): String? {
        return mypref.getString(NotificationStatus, "")
    }
}