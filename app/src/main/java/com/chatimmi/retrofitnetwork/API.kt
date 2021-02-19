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
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("user_type") user_type: String,
    ): Call<UserDetialResponse>


    @POST("api/v1/auth/signup")
    @Multipart
    fun callsignupApi(
            @Part("full_name") fullName: RequestBody?,
            @Part("email") email: RequestBody?,
            @Part("password") password: RequestBody?,
            @Part("user_type") user_type: RequestBody?,
            @Part("confirm_password") confirmPassword: RequestBody?,
            @Part profilePicture: MultipartBody.Part?): Call<UserDetialResponse>


    @FormUrlEncoded
    @PUT("api/v1/auth/forgot-password")
    fun callResetPasswordApi(
            @Field("email") email: String
    ): Call<ResetPasswordResponse>


    @DELETE("api/v1/auth/logout")
    fun callLogoutApi(
    ): Call<LogoutResponse>


    @FormUrlEncoded
    @POST("api/v1/auth/social-signup")
    fun callSocialSignupApi(
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
            @Field("social_id") social_id: String,
            @Field("social_type") social_type: String,
            @Field("user_type") user_type: String,

            ): Call<UserDetialResponse>

    //  api/v1/group/list?group_type=2&offset=0&limit=2
    @GET("api/v1/group/list?")
    fun groupListApi(
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
            @Field("group_id") groupId: String,
    ): Call<JoinGroupResponse>

    @FormUrlEncoded
    @PUT("api/v1/user/change-password")
    fun callChangePasswordApi(
            @Field("new_password") newPassword: String,
            @Field("old_password") oldPassword: String,
            @Field("confirm_password") confirmPassword: String,
    ): Call<ChangePasswordResponse>

    @FormUrlEncoded
    @PUT("api/v1/user/set-password")
    fun callSetPasswordApi(
            @Field("new_password") newPassword: String,
            @Field("confirm_password") confirmPassword: String,
    ): Call<ChangePasswordResponse>

    @GET("api/v1/group/categories")
    fun groupCategoryListApi(
    ): Call<GroupFilterResponse>?


    @GET("api/v1/group/detail?")
    fun groupDetailApi(
            @Query("groupID") groupId: String,
    ): Call<ImmigrationDetailsResponse>?

    @GET("api/v1/user/connectList")
    fun getConsultantList(
            @Query("user_type") user_type: String,
            @Query("offset") offset: String,
            @Query("limit") limit: String,
    ): Call<ConsultantListResponce>?


    @POST("api/v1/group/consultant-connect")
    @FormUrlEncoded
    fun setConsultantConnect(
            @Field("user_id") user_id: String,
    ): Call<JsonObject>?
}