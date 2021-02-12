package com.chatimmi.usermainfragment.group.immigration
import com.google.gson.annotations.SerializedName


data class GroupListResponse(
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("data")
    var `data`: Data? = Data()
) {
    data class Data(
        @SerializedName("group_list")
        var groupList: List<Group> = listOf(),
        @SerializedName("count")
        var count: Int? = 0
    ) {






        data class Group(
            @SerializedName("groupID")
            var groupID: Int? = 0,
            @SerializedName("group_name")
            var groupName: String? = "",
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
            @SerializedName("category_name")
            var categoryName: String? = "",
            @SerializedName("sub_category_name")
            var subCategoryName: String? = "",
            @SerializedName("member_list")
            var memberList: List<Member> = listOf(),
            @SerializedName("categories")
            var categories: Categories? = Categories(),
            @SerializedName("subcategories")
            var subcategories: Subcategories? = Subcategories()

        ) {
            data class Member(
                @SerializedName("userID")
                var userID: Int? = 0,
                @SerializedName("full_name")
                var fullName: String? = "",
                @SerializedName("avatar")
                var avatar: String? = "",
                @SerializedName("status")
                var status: Int? = 0,
                @SerializedName("group_status")
                var groupStatus: Int? = 0
            )

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
            )

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
            )
        }
    }
}