package io.android.fanito_androidclient.date.model.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterVoteRequest(
    @Expose @SerializedName("userId") val userId: String,
    @Expose @SerializedName("pollId") val pollId: String,
    @Expose @SerializedName("optionId") val optionId: Int,
    @Expose @SerializedName("weight") val weight: Int,
    @Expose @SerializedName("createdAt") val createdAt: String,
)