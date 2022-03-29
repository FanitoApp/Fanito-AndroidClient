package io.android.fanito_androidclient.date.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose @SerializedName("refreshToken") val refreshToken: String,
    @Expose @SerializedName("accessToken") val accessToken: String,
    @Expose @SerializedName("tokenType") val tokenType: String,
)