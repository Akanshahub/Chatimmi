package com.chatimmi.usermainfragment.group.immigration.details


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ImmigrationDetailsResponse(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("groupData")
        var groupData: GroupData? = GroupData(),
        @SerializedName("group_member")
        var groupMember: List<GroupMember?>? = listOf(),
        @SerializedName("is_group_connect")
        var isGroupConnect: Int? = 0,
        @SerializedName("category")
        var category: Category? = Category(),
        @SerializedName("subcategory")
        var subcategory: Subcategory? = Subcategory()
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class GroupData(
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
            @SerializedName("userGroupMapID")
            var userGroupMapID: Int? = 0,
            @SerializedName("user_id")
            var userId: Int? = 0,
            @SerializedName("group_id")
            var groupId: Int? = 0,
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
            var dob: String? = "",
            @SerializedName("language")
            var language: String? = "",
            @SerializedName("contact_number")
            var contactNumber: String? = "",
            @SerializedName("country_code")
            var countryCode: String? = "",
            @SerializedName("country_dial")
            var countryDial: String? = "",
            @SerializedName("bio")
            var bio: String? = "",
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
            var lastLoginAt: String? = ""
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class GroupMember(
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
            @SerializedName("is_connect")
            var isConnect: Int? = 0
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Category(
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
        data class Subcategory(
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
}