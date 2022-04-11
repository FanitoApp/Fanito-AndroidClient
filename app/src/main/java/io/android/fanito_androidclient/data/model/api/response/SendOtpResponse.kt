package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    @Expose @SerializedName("id") val id: String,
    @Expose @SerializedName("expiresAt") val expiresAt: Long,
)
