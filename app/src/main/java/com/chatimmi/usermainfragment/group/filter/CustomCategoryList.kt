


import com.google.gson.annotations.SerializedName
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class CustomCategoryList(
        @SerializedName("categoryId")
        var categoryID: Int,
        @SerializedName("isSelected")
        var isSelected: Boolean
)  : Parcelable {
            @Parcelize
            data class Subcategory(
                    @SerializedName("categoryId")
                    var categoryID: Int,
                    @SerializedName("isSelected")
                    var isSelected: Boolean,
                    @SerializedName("subCategoryId")
                    var subCategoryId: Int

            ) : Parcelable
        }

