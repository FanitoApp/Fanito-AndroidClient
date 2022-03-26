package io.android.fanito_androidclient.date.model.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @Expose @SerializedName("sessionId") val sessionId: String,
    @Expose @SerializedName("phone") val phone: String,
    @Expose @SerializedName("otp") val otp: String,
)