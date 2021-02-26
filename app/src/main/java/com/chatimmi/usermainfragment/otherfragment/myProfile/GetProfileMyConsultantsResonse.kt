package com.chatimmi.usermainfragment.otherfragment.myProfile


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GetProfileMyConsultantsResonse(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("user_details")
        var userDetails: UserDetails? = UserDetails(),
        @SerializedName("list")
        var list: List<MyConsultantsList> = listOf(),
        @SerializedName("count")
        var count: Int? = 0
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class UserDetails(
            @SerializedName("userID")
            var userID: Int? = 0,
            @SerializedName("full_name")
            var fullName: String? = "",
            @SerializedName("email")
            var email: String? = "",
            @SerializedName("password")
            var password: String? = "",
            @SerializedName("user_type")
            var userType: Int? = 0,
            @SerializedName("avatar")
            var avatar: String? = "",
            @SerializedName("is_avatar_url")
            var isAvatarUrl: Int? = 0,
            @SerializedName("gender")
            var gender: Int? = 0,
            @SerializedName("dob")
            var dob: Int? = 0,
            @SerializedName("language")
            var language: String? = "",
            @SerializedName("contact_number")
            var contactNumber: String? = "",
            @SerializedName("country_code")
            var countryCode: Int? = 0,
            @SerializedName("country_dial")
            var countryDial: Int? = 0,
            @SerializedName("bio")
            var bio: Int? = 0,
            @SerializedName("alert_count")
            var alertCount: Int? = 0,
            @SerializedName("email_verification_token")
            var emailVerificationToken: Int? = 0,
            @SerializedName("password_token")
            var passwordToken: Int? = 0,
            @SerializedName("email_verified")
            var emailVerified: Int? = 0,
            @SerializedName("signup_from")
            var signupFrom: Int? = 0,
            @SerializedName("signup_type")
            var signupType: Int? = 0,
            @SerializedName("push_alert_status")
            var pushAlertStatus: Int? = 0,
            @SerializedName("last_login_at")
            var lastLoginAt: String? = "",
            @SerializedName("status")
            var status: Int? = 0,
            @SerializedName("updated_at")
            var updatedAt: String? = "",
            @SerializedName("created_at")
            var createdAt: String? = ""
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class MyConsultantsList (
            @SerializedName("full_name")
            var fullName: String? = "",
            @SerializedName("avatar")
            var avatar: String? = "",
            @SerializedName("userID")
            var userID: Int? = 0,
            @SerializedName("email")
            var email: String? = "",
            @SerializedName("connectionID")
            var connectionID: Int? = 0,
            @SerializedName("request_from")
            var requestFrom: Int? = 0,
            @SerializedName("request_to")
            var requestTo: Int? = 0,
            @SerializedName("last_message")
            var lastMessage: Int? = 0,
            @SerializedName("unread_count_for_consultant")
            var unreadCountForConsultant: Int? = 0,
            @SerializedName("unread_count_for_user")
            var unreadCountForUser: Int? = 0,
            @SerializedName("is_block")
            var isBlock: Int? = 0,
            @SerializedName("category_name")
            var categoryname: String? = "",
            @SerializedName("created_at")
            var createdAt: String? = "",
            @SerializedName("updated_at")
            var updatedAt: String? = ""

        ) : Parcelable
    }
}