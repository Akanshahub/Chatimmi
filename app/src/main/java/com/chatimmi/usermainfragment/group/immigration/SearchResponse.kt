package com.chatimmi.usermainfragment.group.immigration

import android.os.Parcelable
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



@Parcelize
data class SearchResponse(
        @SerializedName("category")
        var category: String?=null,
        @SerializedName("subcategory")
        var subcategory: String? =null,
        @SerializedName("group_scope")
        var group_scope: String? =null,
) : Parcelable
