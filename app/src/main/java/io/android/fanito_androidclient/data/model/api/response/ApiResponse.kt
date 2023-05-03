package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @Expose @SerializedName("code") val code: Int,
    @Expose @SerializedName("message") val message: String?,
    @Expose @SerializedName("meta") val meta: T?,
)