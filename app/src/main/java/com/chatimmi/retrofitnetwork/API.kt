package com.chatimmi.retrofitnetwork

import com.chatimmi.model.LoginRegistrationResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.model.UserDetialResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*

interface API {
    @FormUrlEncoded
    @POST("api/v1/auth/login")
    fun callLoginApi(
            @Header("device-id") deviceId: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") deviceTimeZone: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("user_type") user_type: String,
            @Header("device-token") device_token: String,

    ): Call<LoginRegistrationResponse>



    @POST("api/v1/auth/signup")
    @Multipart
    fun callsignupApi(
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") deviceTimeZone: String,
            @Part("full_name") fullName: RequestBody?,
            @Part("email") email: RequestBody?,
            @Part("password") password: RequestBody?,
            @Part("user_type") user_type: RequestBody?,
            @Part("confirm_password") confirmPassword: RequestBody?,
            @Part profilePicture:  MultipartBody.Part?



            ): Call<UserDetialResponse>


    @FormUrlEncoded
    @PUT("api/v1/auth/forgot-password")
    fun callResetPasswordApi(
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceType: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") timezone: String,
            @Field("email") email: String
    ): Call<ResetPasswordResponse>


    @DELETE("api/v1/auth/logout")
    fun callLogoutApi(
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceType: String,
            @Header("device-type") deviceTimeZone: String,
            @Header("timezone") timezone: String
    ): Call<LogoutResponse>

}