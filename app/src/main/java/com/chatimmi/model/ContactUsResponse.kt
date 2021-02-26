package com.chatimmi.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ContactUsResponse(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("message")
    var message: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    class Data(
    ) : Parcelable
}