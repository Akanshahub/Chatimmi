package com.chatimmi.retrofitnetwork

import com.chatimmi.helper.joindailong.JoinGroupResponse
import com.chatimmi.model.ChangePasswordResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.model.ResetPasswordResponse
import com.chatimmi.model.UserDetialResponse
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ConsultantListResponce
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsResponse
import com.google.gson.JsonObject
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

            ): Call<UserDetialResponse>


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
            @Part profilePicture: MultipartBody.Part?): Call<UserDetialResponse>


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

    /*   full_name:sachin social
       email:spd@gmail.comdfgffffdnn
       password:123456
       user_type:2
       social_id:125dfgfsdfgffffhcccxxzhjj
       social_type:1
       profilePicture:fghgh*/
    @FormUrlEncoded
    @POST("api/v1/auth/social-signup")
    fun callSocialSignupApi(
            @Header("device-id") deviceId: String,
            @Header("device-token") devicetoken: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") deviceTimeZone: String,
            @Field("full_name") fullName: String,
            @Field("email") email: String,
            @Field("user_type") userType: String,
            @Field("social_id") socialId: String,
            @Field("social_type") socialType: String,
            @Field("profilePicture") profileImage: String,
    ): Call<UserDetialResponse>


    @FormUrlEncoded
    @POST("api/v1/auth/check-social-status")
    fun callCheckSocialSignupApi(
            @Header("device-id") deviceId: String,
            @Header("device-token") devicetoken: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") timezone: String,
            @Field("social_id") social_id: String,
            @Field("social_type") social_type: String,
            @Field("user_type") user_type: String,

            ): Call<UserDetialResponse>

    //  api/v1/group/list?group_type=2&offset=0&limit=2
    @GET("api/v1/group/list?")
    fun groupListApi(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceType: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") timezone: String,
            @Query("group_type") grouptype: String,
            @Query("category") category: String,
            @Query("subcategory") subcategory: String,
            @Query("group_scope") group_scope: String,
            /*  @Query("social_type") social_type: String,
              @Query("user_type") user_type: String,*/

    ): Call<GroupListResponse>?

    @FormUrlEncoded
    @POST("api/v1/group/connect")
    fun callConnectGroupApi(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") timezone: String,
            @Field("group_id") groupId: String,
    ): Call<JoinGroupResponse>

    @FormUrlEncoded
    @PUT("api/v1/user/change-password")
    fun callChangePasswordApi(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") timezone: String,
            @Field("new_password") newPassword: String,
            @Field("old_password") oldPassword: String,
            @Field("confirm_password") confirmPassword: String,
    ): Call<ChangePasswordResponse>

    @FormUrlEncoded
    @PUT("api/v1/user/set-password")
    fun callSetPasswordApi(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") timezone: String,
            @Field("new_password") newPassword: String,
            @Field("confirm_password") confirmPassword: String,
    ): Call<ChangePasswordResponse>

    @GET("api/v1/group/categories")
    fun groupCategoryListApi(

            @Header("device-id") deviceId: String,
            @Header("device-token") deviceType: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") timezone: String,


            ): Call<GroupFilterResponse>?


    @GET("api/v1/group/detail?")
    fun groupDetailApi(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") timezone: String,
            @Query("groupID") groupId: String,


            ): Call<ImmigrationDetailsResponse>?

    @GET("api/v1/user/connectList")
    fun getConsultantList(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceToken: String,
            @Header("device-type") deviceType: String,
            @Header("timezone") timezone: String,
            @Query("user_type") user_type: String,
            @Query("offset") offset: String,
            @Query("limit") limit: String,
            ): Call<ConsultantListResponce>?


    @POST("api/v1/group/consultant-connect")
    @FormUrlEncoded
    fun setConsultantConnect(
            @Header("Authorization") authToken: String,
            @Header("device-id") deviceId: String,
            @Header("device-token") deviceType: String,
            @Header("device-type") devicetype: String,
            @Header("timezone") timezone: String,
            @Field("user_id") user_id: String,
    ): Call<JsonObject>?
}