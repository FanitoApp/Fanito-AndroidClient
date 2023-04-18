package io.android.fanito_androidclient.data.model.api.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.android.fanito_androidclient.utils.IComparable
data class PortfolioResponse(
    @Expose @SerializedName("description") val description: String?,
    @Expose @SerializedName("logoURL") val logoURL: String?,
    @Expose @SerializedName("title") val title: String?,
    @Expose @SerializedName("price") val price: Long?,
    @Expose @SerializedName("symbol") val symbol: String?,
    @Expose @SerializedName("balance") val balance: Long?,
) : IComparable {

    override fun comparator() = symbol
}