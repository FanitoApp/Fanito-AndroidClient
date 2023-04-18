package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.AppConstants.POLL_STATE_ACTIVE
import io.android.fanito_androidclient.AppConstants.POLL_STATE_ENDED
import io.android.fanito_androidclient.AppConstants.POLL_STATE_PENDING
import io.android.fanito_androidclient.utils.IComparable

data class GetPollsResponse(
    @Expose @SerializedName("totalCount") val totalCount: Int?,
    @Expose @SerializedName("items") val items: List<Poll>?,
) {

    data class Poll(
        @Expose @SerializedName("id") val id: String?,
        @Expose @SerializedName("title") val title: String?,
        @Expose @SerializedName("description") val description: String?,
        @Expose @SerializedName("logo") val pictureURL: String?,
        @Expose @SerializedName("issuerId") val issuerId: String?,
        @Expose @SerializedName("tokenSymbol") val tokenSymbol: String?,
        @Expose @SerializedName("startTime") val startTime: String?,
        @Expose @SerializedName("endTime") val endTime: String?,
        @Expose @SerializedName("userMaxWeight") val userMaxWeight: Int?,
        @Expose @SerializedName("participantCount") val participantCount: Long?,
        @Expose @SerializedName("totalTokens") val totalTokens: Int?,
        @Expose @SerializedName("status") val status: String?,
    ) : IComparable {

        override fun comparator() = id

        fun getStateTitle(): Int {
            return when (status) {
                POLL_STATE_ACTIVE -> R.string.active
                POLL_STATE_ENDED -> R.string.ended
                POLL_STATE_PENDING -> R.string.polls_pending
                else -> R.string.empty
            }
        }

        fun getStateColor(): Int {
            return when (status) {
                POLL_STATE_ACTIVE -> R.color.teal_A400
                POLL_STATE_ENDED -> R.color.pink_500
                POLL_STATE_PENDING -> R.string.polls_pending
                else -> R.string.empty
            }
        }
    }
}