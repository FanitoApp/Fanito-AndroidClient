package io.android.fanito_androidclient.date.model.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @Expose @SerializedName("phone") val phone: String,
)
