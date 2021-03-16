package com.chatimmi.usermainfragment.connectfragment


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Chat(
        @SerializedName("status")
        var status: String? = "",
        @SerializedName("data")
        var `data`: Data? = Data(),
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
            @SerializedName("messageData")
            var messageData: ArrayList<MessageData?>? = arrayListOf(),
            @SerializedName("is_block")
            var isBlock: Int? = 0,
            @SerializedName("is_online")
            var isOnline: Int? = 0
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class MessageData(
                @SerializedName("messageID")
                var messageID: Int? = 0,
                @SerializedName("sender_id")
                var senderId: String? = "",
                @SerializedName("reciever_id")
                var recieverId: Int? = 0,
                @SerializedName("group_id")
                var groupId: Int? = 0,
                @SerializedName("message")
                var message: String? = "",
                @SerializedName("is_image")
                var isImage: Int? = 0,
                @SerializedName("image_url")
                var imageUrl: String? = "",
                @SerializedName("is_read")
                var isread: Int? = 0,
                @SerializedName("created_on")
                var createdOn: String? = "",
                @SerializedName("updated_at")
                var updatedAt: String? = "",
                var numberOfToday: Int = 0,
                var numberOfYesterday: Int = 0,
                var dayName: String = "",
                var shouldVisibleShowDateView:Boolean = false
        ) : Parcelable
    }
}