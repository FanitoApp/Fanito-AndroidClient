package io.android.fanito_androidclient.data.model.others

import io.android.fanito_androidclient.IComparable

data class FanClubToken(
    var id: Long,
    var clubName: String,
    var clubPhotoUrl: String? = null,
    var price: Long = 0L,
    var percent: Float = 0F,
    var participants: Long = 0L
) : IComparable {

    override fun comparator() = id
}