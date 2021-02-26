package com.chatimmi.usermainfragment.otherfragment.myProfile


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GetProfileChatGroupResponse(
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
            var list: List<MyChatGroupList> = listOf(),
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
        data class MyChatGroupList(
                @SerializedName("groupID")
                var groupID: Int? = 0,
                @SerializedName("group_name")
                var groupName: String? = "",
                @SerializedName("group_description")
                var groupDescription: String? = "",
                @SerializedName("group_scope")
                var groupScope: Int? = 0,
                @SerializedName("category_id")
                var categoryId: Int? = 0,
                @SerializedName("subcategory_id")
                var subcategoryId: Int? = 0,
                @SerializedName("group_type")
                var groupType: Int? = 0,
                @SerializedName("group_image")
                var groupImage: String? = "",
                @SerializedName("status")
                var status: Int? = 0,
                @SerializedName("is_delete")
                var isDelete: Int? = 0,
                @SerializedName("created_at")
                var createdAt: String? = "",
                @SerializedName("updated_at")
                var updatedAt: String? = "",
                @SerializedName("categoryID")
                var categoryID: Int? = 0,
                @SerializedName("name")
                var name: String? = "",
                @SerializedName("category_type")
                var categoryType: Int? = 0,
                @SerializedName("parent_category_id")
                var parentCategoryId: Int? = 0,
                @SerializedName("userGroupMapID")
                var userGroupMapID: Int? = 0,
                @SerializedName("user_id")
                var userId: Int? = 0,
                @SerializedName("group_id")
                var groupId: Int? = 0,
                @SerializedName("is_deleted")
                var isDeleted: Int? = 0,
                @SerializedName("category_name")
                var categoryName: String? = "",
                @SerializedName("sub_category_name")
                var subCategoryName: String? = "",
                @SerializedName("is_group_connect")
                var isGroupConnect: Int? = 0,
                @SerializedName("member_list")
                var memberList: List<Member?>? = listOf(),
                @SerializedName("categories")
                var categories: Categories? = Categories(),
                @SerializedName("subcategories")
                var subcategories: Subcategories? = Subcategories()
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Member(
                    @SerializedName("userID")
                    var userID: Int? = 0,
                    @SerializedName("full_name")
                    var fullName: String? = "",
                    @SerializedName("user_type")
                    var userType: Int? = 0,
                    @SerializedName("avatar")
                    var avatar: String? = "",
                    @SerializedName("is_avatar_url")
                    var isAvatarUrl: Int? = 0,
                    @SerializedName("status")
                    var status: Int? = 0,
                    @SerializedName("group_status")
                    var groupStatus: Int? = 0,
                    @SerializedName("is_deleted")
                    var isDeleted: Int? = 0
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Categories(
                    @SerializedName("categoryID")
                    var categoryID: Int? = 0,
                    @SerializedName("name")
                    var name: String? = "",
                    @SerializedName("category_type")
                    var categoryType: Int? = 0,
                    @SerializedName("parent_category_id")
                    var parentCategoryId: Int? = 0,
                    @SerializedName("status")
                    var status: Int? = 0,
                    @SerializedName("is_delete")
                    var isDelete: Int? = 0,
                    @SerializedName("created_at")
                    var createdAt: String? = "",
                    @SerializedName("updated_at")
                    var updatedAt: String? = ""
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Subcategories(
                    @SerializedName("categoryID")
                    var categoryID: Int? = 0,
                    @SerializedName("name")
                    var name: String? = "",
                    @SerializedName("category_type")
                    var categoryType: Int? = 0,
                    @SerializedName("parent_category_id")
                    var parentCategoryId: Int? = 0,
                    @SerializedName("status")
                    var status: Int? = 0,
                    @SerializedName("is_delete")
                    var isDelete: Int? = 0,
                    @SerializedName("created_at")
                    var createdAt: String? = "",
                    @SerializedName("updated_at")
                    var updatedAt: String? = ""
            ) : Parcelable
        }
    }}