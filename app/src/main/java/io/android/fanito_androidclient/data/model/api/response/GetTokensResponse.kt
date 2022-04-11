package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.android.fanito_androidclient.IComparable

data class GetTokensResponse(
    @Expose @SerializedName("totalCount") val totalCount: Int?,
    @Expose @SerializedName("items") val items: List<Token>?,
) {

    data class Token(
        @Expose @SerializedName("description") val description: String?,
        @Expose @SerializedName("logoURL") val logoURL: String?,
        @Expose @SerializedName("title") val title: String?,
        @Expose @SerializedName("issuerId") val issuerId: String?,
        @Expose @SerializedName("price") val price: Long?,
        @Expose @SerializedName("symbol") val symbol: String?,
        @Expose @SerializedName("startTime") val startTime :String?,
        @Expose @SerializedName("endTime") val endTime :String?,
        @Expose @SerializedName("soldCap") val soldCap :Long?,
        @Expose @SerializedName("maxCapPerUser") val maxCapPerUser :Int?,
        @Expose @SerializedName("userBalance") var userBalance: Long = 0,
        @Expose @SerializedName("imageURL") var imageURL: String?
    ) : IComparable {

        override fun comparator() = symbol
    }
}