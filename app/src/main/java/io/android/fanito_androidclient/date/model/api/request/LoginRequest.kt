package io.android.fanito_androidclient.date.model.api.request

data class LoginRequest(
    @Expose @SerializedName("otpSessionId") val otpSessionId: String,
)