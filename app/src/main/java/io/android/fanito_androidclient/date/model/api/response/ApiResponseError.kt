package io.android.fanito_androidclient.date.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponseError(
    @Expose @SerializedName("code") val code: Int,
    @Expose @SerializedName("message") val message: String?,
)