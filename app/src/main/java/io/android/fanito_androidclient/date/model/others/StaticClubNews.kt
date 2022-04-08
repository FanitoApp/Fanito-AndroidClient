package io.android.fanito_androidclient.date.model.others

import android.graphics.drawable.Drawable
import io.android.fanito_androidclient.utils.IComparable

data class StaticClubNews(
    var id: Long,
    var drawable: Drawable?,
    var title: String? = null,
    var descrption: String? = null,
    var drawableId: Int=0,
) : IComparable {

    override fun comparator() = id
}