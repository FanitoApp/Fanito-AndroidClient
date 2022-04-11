package io.android.fanito_androidclient.data.model.api.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.android.fanito_androidclient.IComparable

data class PollOptionsResponse(
    @Expose @SerializedName("totalCount") val totalCount: Int?,
    @Expose @SerializedName("items") val items: List<Option>?,
) {

    data class Option(
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("pictureURL") val pictureURL: String?,
        @Expose @SerializedName("title") val title: String?,
        @Expose @SerializedName("description") val description: String?,
        @Expose @SerializedName("count") val count: Int?,
        @Expose @SerializedName("tokens") val tokens: Int?,
        @Expose @SerializedName("weight") val weight: Int?,
        var isSelected: Boolean,
        var isVoted: Boolean,
        var isSending: Boolean,
    ) : IComparable {

        override fun comparator() = id

        fun changeSelectState(isSelected: Boolean) {
            this.isSelected = isSelected
        }

        fun changeVoteState(isVoted: Boolean) {
            this.isVoted = isVoted
        }

        fun changeSendingState(isSending: Boolean) {
            this.isSending = isSending
        }
    }
}