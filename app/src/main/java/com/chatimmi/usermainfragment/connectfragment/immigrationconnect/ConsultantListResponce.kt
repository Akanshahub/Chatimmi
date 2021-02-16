package com.chatimmi.usermainfragment.connectfragment.immigrationconnect


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ConsultantListResponce(
        @SerializedName("status")
        var status: String = "",
        @SerializedName("data")
        var `data`: Data = Data()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
            @SerializedName("consultant_list")
            var consultantList: List<Consultant> = listOf(),
            @SerializedName("count")
            var count: Int = 0
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Consultant(
                @SerializedName("userID")
                var userID: Int = 0,
                @SerializedName("full_name")
                var fullName: String = "",
                @SerializedName("email")
                var email: String = "",
                @SerializedName("password")
                var password: String = "",
                @SerializedName("user_type")
                var userType: Int = 0,
                @SerializedName("avatar")
                var avatar: String = "",
                @SerializedName("is_avatar_url")
                var isAvatarUrl: Int = 0,
                @SerializedName("gender")
                var gender: Int = 0,
                @SerializedName("dob")
                var dob: String = "",
                @SerializedName("language")
                var language: String = "",
                @SerializedName("contact_number")
                var contactNumber: String = "",
                @SerializedName("country_code")
                var countryCode: String = "",
                @SerializedName("country_dial")
                var countryDial: String = "",
                @SerializedName("bio")
                var bio: String = "",
                @SerializedName("alert_count")
                var alertCount: Int = 0,
                @SerializedName("email_verification_token")
                var emailVerificationToken: Int = 0,
                @SerializedName("password_token")
                var passwordToken: Int = 0,
                @SerializedName("email_verified")
                var emailVerified: Int = 0,
                @SerializedName("signup_from")
                var signupFrom: Int = 0,
                @SerializedName("signup_type")
                var signupType: Int = 0,
                @SerializedName("push_alert_status")
                var pushAlertStatus: Int = 0,
                @SerializedName("last_login_at")
                var lastLoginAt: String = "",
                @SerializedName("status")
                var status: Int = 0,
                @SerializedName("updated_at")
                var updatedAt: String = "",
                @SerializedName("created_at")
                var createdAt: String = "",
                @SerializedName("user_id")
                var userId: Int = 0,
                @SerializedName("category_id")
                var categoryId: Int = 0,
                @SerializedName("categoryID")
                var categoryID: Int = 0,
                @SerializedName("name")
                var name: String = "",
                @SerializedName("category_type")
                var categoryType: Int = 0,
                @SerializedName("parent_category_id")
                var parentCategoryId: Int = 0,
                @SerializedName("is_delete")
                var isDelete: Int = 0,
                @SerializedName("category_name")
                var categoryName: String = "",
                @SerializedName("sub_category_name")
                var subCategoryName: String = "",
                @SerializedName("is_connect")
                var isConnect: Int = 0,
                @SerializedName("categories")
                var categories: List<Category> = listOf(),
                @SerializedName("subcategories")
                var subcategories: List<Subcategory> = listOf()
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Category(
                    @SerializedName("categoryID")
                    var categoryID: Int = 0,
                    @SerializedName("name")
                    var name: String = "",
                    @SerializedName("category_type")
                    var categoryType: Int = 0,
                    @SerializedName("parent_category_id")
                    var parentCategoryId: Int = 0,
                    @SerializedName("status")
                    var status: Int = 0,
                    @SerializedName("is_delete")
                    var isDelete: Int = 0,
                    @SerializedName("created_at")
                    var createdAt: String = "",
                    @SerializedName("updated_at")
                    var updatedAt: String = "",
                    @SerializedName("user_id")
                    var userId: Int = 0,
                    @SerializedName("category_id")
                    var categoryId: Int = 0
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Subcategory(
                    @SerializedName("categoryID")
                    var categoryID: Int = 0,
                    @SerializedName("name")
                    var name: String = "",
                    @SerializedName("category_type")
                    var categoryType: Int = 0,
                    @SerializedName("parent_category_id")
                    var parentCategoryId: Int = 0,
                    @SerializedName("status")
                    var status: Int = 0,
                    @SerializedName("is_delete")
                    var isDelete: Int = 0,
                    @SerializedName("created_at")
                    var createdAt: String = "",
                    @SerializedName("updated_at")
                    var updatedAt: String = "",
                    @SerializedName("user_id")
                    var userId: Int = 0,
                    @SerializedName("category_id")
                    var categoryId: Int = 0
            ) : Parcelable
        }
    }
}