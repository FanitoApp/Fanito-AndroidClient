package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.utils.AppConstants.TX_TYPE_DEPOSIT
import io.android.fanito_androidclient.utils.AppConstants.TX_TYPE_PURCHASE
import io.android.fanito_androidclient.utils.AppConstants.TX_TYPE_WITHDRAW
import io.android.fanito_androidclient.utils.IComparable

data class GetTxsResponse(
    @Expose @SerializedName("totalCount") val totalCount: Int?,
    @Expose @SerializedName("items") val items: List<Tx>?,
) {

    data class Tx(
        @Expose @SerializedName("id") val id: String,
        @Expose @SerializedName("from") val from: String?,
        @Expose @SerializedName("from_address") val fromAddress: String?,
        @Expose @SerializedName("to") val to: String?,
        @Expose @SerializedName("to_address") val toAddress: String?,
        @Expose @SerializedName("chain") val chain: String?,
        @Expose @SerializedName("token") val token: String?,
        @Expose @SerializedName("type") val type: String?,
        @Expose @SerializedName("status") val status: String?,
        @Expose @SerializedName("amount") val amount: Int?,
        @Expose @SerializedName("createdAt") val createdAt: String?,
    ) : IComparable {

        override fun comparator() = id

        fun getTypeTitle(): Int {
            return when (type) {
                TX_TYPE_PURCHASE -> R.string.buy_token
                TX_TYPE_WITHDRAW -> R.string.withdraw
                TX_TYPE_DEPOSIT -> R.string.deposit
                else -> R.string.empty
            }
        }

        fun getTypeColor(): Int {
            return when (type) {
                TX_TYPE_PURCHASE -> R.color.yellow
                TX_TYPE_WITHDRAW -> R.color.red_400
                TX_TYPE_DEPOSIT -> R.color.green_A400
                else -> android.R.color.transparent
            }
        }

        fun getTypeIcon(): Int {
            return when (type) {
                TX_TYPE_PURCHASE -> R.drawable.ic_buy_token
                TX_TYPE_WITHDRAW -> R.drawable.ic_withdraw
                TX_TYPE_DEPOSIT -> R.drawable.ic_deposit
                else -> android.R.color.transparent
            }
        }
    }
}
