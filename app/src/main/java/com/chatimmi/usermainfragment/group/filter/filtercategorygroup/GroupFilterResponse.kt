package com.chatimmi.usermainfragment.group.filter.filtercategorygroup


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class GroupFilterResponse(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("category")
        var category: List<Category> = listOf()
    ) : Parcelable {
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
            @SerializedName("count")
            var count: Int? = 0,
            @SerializedName("created_at")
            var createdAt: String? = "",
            @SerializedName("updated_at")
            var updatedAt: String? = "",
            @SerializedName("subcategories")
            var subcategories: List<Subcategory> = listOf(),
            var isSelect:Boolean?=false

        ) : Parcelable {
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
                @SerializedName("is_selected")
                var isselected: Boolean?=false ,
                @SerializedName("created_at")
                var createdAt: String? = "",
                @SerializedName("updated_at")
                var updatedAt: String? = "" ,
                    @SerializedName("count")
                var count: Int? = 0
            ) : Parcelable
        }
    }
}