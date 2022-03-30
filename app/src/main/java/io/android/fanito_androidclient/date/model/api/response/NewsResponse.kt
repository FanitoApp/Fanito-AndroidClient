package io.android.fanito_androidclient.date.model.api.response

import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @Expose@SerializedName("title") val title: String?,
    @Expose@SerializedName("description") val description: String?,
    @Expose @SerializedName("imageURL") var imageURL: String?,
    //It is temporary and not related to the server
    @Expose @SerializedName("drawbleId") var drawbleId: Int?,
    @Expose(serialize = false, deserialize = false)   var drawable: Drawable?


)