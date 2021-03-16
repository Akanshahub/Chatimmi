package com.chatimmi.usermainfragment.chat


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ChatHistoryResponse(
        @SerializedName("status")
        var status: String? = "",
        @SerializedName("data")
        var `data`: Data? = Data(),
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
            @SerializedName("history_list")
            var historyList: List<History> = listOf(),
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class History(
                @SerializedName("full_name")
                var fullName: String? = "",
                @SerializedName("avatar")
                var avatar: String? = "",
                @SerializedName("userID")
                var userID: Int? = 0,
                @SerializedName("email")
                var email: String? = "",
                @SerializedName("chatMemberID")
                var chatMemberID: Int? = 0,
                @SerializedName("user_id")
                var userId: Int? = 0,
                @SerializedName("user_type")
                var userType: Int? = 0,
                @SerializedName("typing")
                var typing: Int? = 0,
                @SerializedName("status")
                var status: Int? = 0,
                @SerializedName("is_online")
                var isOnline: Int? = 0,
                @SerializedName("created_on")
                var createdOn: String? = "",
                @SerializedName("updated_at")
                var updatedAt: String? = "",
                @SerializedName("connectionID")
                var connectionID: Int? = 0,
                @SerializedName("request_from")
                var requestFrom: Int? = 0,
                @SerializedName("request_to")
                var requestTo: Int? = 0,
                @SerializedName("last_message")
                var lastMessage: String? = "",
                @SerializedName("is_image")
                var isImage: Int? = 0,
                @SerializedName("unread_count_for_consultant")
                var unreadCountForConsultant: Int? = 0,
                @SerializedName("unread_count_for_user")
                var unreadCountForUser: Int? = 0,
                @SerializedName("is_block")
                var isBlock: Int? = 0,
                @SerializedName("created_at")
                var createdAt: String? = "",
                @SerializedName("msgTime")
                var msgTime: String? = "",
                @SerializedName("msg_for_user")
                var msgforuser: String? = "",
        ) : Parcelable
    }
}