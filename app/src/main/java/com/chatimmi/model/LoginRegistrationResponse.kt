package com.chatimmi.model

import com.google.gson.annotations.SerializedName

class LoginRegistrationResponse {



    @field:SerializedName("data")
    val data: Data? = null

    @field:SerializedName("message")
    val message: String? = null

    @field:SerializedName("status")
    val status: String? = null

    data class UserDetails(

            @field:SerializedName("last_login_at")
            val lastLoginAt: String? = null,

            @field:SerializedName("onboarding_step")
            val onboardingStep: String? = null,

            @field:SerializedName("device_timezone")
            val deviceTimezone: String? = null,

            @field:SerializedName("is_email_verified")
            val isEmailVerified: String? = null,

            @field:SerializedName("created_at")
            val createdAt: String? = null,

            @field:SerializedName("device_type")
            val deviceType: String? = null,

            @field:SerializedName("phone_dial_code")
            val phoneDialCode: String? = null,

            @field:SerializedName("userID")
            val userID: String? = null,

            @field:SerializedName("password")
            val password: String? = null,

            @field:SerializedName("user_type")
            val userType: String? = null,

            @field:SerializedName("profile_timezone")
            val profileTimezone: String? = null,

            @field:SerializedName("updated_at")
            val updatedAt: String? = null,

            @field:SerializedName("is_profile_url")
            val isProfileUrl: String? = null,

            @field:SerializedName("first_name")
            val firstName: String? = null,

            @field:SerializedName("email")
            val email: String? = null,

            @field:SerializedName("phone_country_code")
            val phoneCountryCode: String? = null,

            @field:SerializedName("device_id")
            val deviceId: String? = null,

            @field:SerializedName("last_name")
            val lastName: String? = null,

            @field:SerializedName("profile_picture")
            val profilePicture: String? = null,

            @field:SerializedName("firebase_token")
            val firebaseToken: String? = null,

            @field:SerializedName("signup_type")
            val signupType: String? = null,

            @field:SerializedName("push_alert_status")
            val pushAlertStatus: String? = null,

            @field:SerializedName("is_onboarding_completed")
            val isOnboardingCompleted: String? = null,

            @field:SerializedName("phone_number")
            val phoneNumber: Any? = null,

            @field:SerializedName("auth_token")
            val authToken: String? = null,

            @field:SerializedName("status")
            val status: String? = null
    )

    data class Data(

            @field:SerializedName("user_details")
            val userDetails: UserDetails? = null
    )

    override fun toString(): String {
        return "LoginRegistrationResponse( data=$data, message=$message, status=$status)"
    }


}