package io.android.fanito_androidclient.date.model.api.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetailsResponse  (
    @Expose @SerializedName("id") val id: String?,
    @Expose @SerializedName("name") val name: String?,
    @Expose @SerializedName("pictureURL") val pictureURL: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int =0

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(this.id)
        p0.writeString(this.name)
        p0.writeString(this.pictureURL)  }

    companion object CREATOR : Parcelable.Creator<UserDetailsResponse> {
        override fun createFromParcel(parcel: Parcel): UserDetailsResponse {
            return UserDetailsResponse(parcel)
        }

        override fun newArray(size: Int): Array<UserDetailsResponse?> {
            return arrayOfNulls(size)
        }
    }

}