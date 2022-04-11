package io.android.fanito_androidclient.data.model.api.request

data class LoginRequest(
    @Expose @SerializedName("otpSessionId") val otpSessionId: String,
)