package io.android.fanito_androidclient.data.model.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserPersonalResponse(
    @Expose @SerializedName("phone") val phone: String?,
    @Expose @SerializedName("firstName") val firstName: String?,
    @Expose @SerializedName("lastName") val lastName: String?,
    @Expose @SerializedName("nationalId") val nationalId: String?,
    @Expose @SerializedName("birthDate") val birthDate: String?,
    @Expose @SerializedName("createdAt") val createdAt: String?,
)