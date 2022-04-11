package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @Expose @SerializedName("sessionId") val sessionId: String,
    @Expose @SerializedName("registered") val registered: Boolean,
    @Expose @SerializedName("expiresAt") val expiresAt: Long,
)