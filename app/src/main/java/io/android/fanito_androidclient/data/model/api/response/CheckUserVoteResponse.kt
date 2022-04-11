package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckUserVoteResponse(
    @Expose @SerializedName("userId") val userId: String?,
    @Expose @SerializedName("pollId") val pollId: String?,
    @Expose @SerializedName("createdAt") val createdAt: String?,
    @Expose @SerializedName("optionId") val optionId: Int?,
    @Expose @SerializedName("weight") val weight: Int?,
)