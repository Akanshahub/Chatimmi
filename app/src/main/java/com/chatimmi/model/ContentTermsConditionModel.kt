package com.chatimmi.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ContentTermsConditionModel(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("terms_url")
        var termsUrl: String? = "",
        @SerializedName("privacy_url")
        var privacyUrl: String? = ""
    ) : Parcelable
}