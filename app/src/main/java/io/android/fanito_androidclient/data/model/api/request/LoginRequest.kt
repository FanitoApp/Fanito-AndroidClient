package io.android.fanito_androidclient.data.model.api.request
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class LoginRequest(
    @Expose @SerializedName("otpSessionId") val otpSessionId: String,
)