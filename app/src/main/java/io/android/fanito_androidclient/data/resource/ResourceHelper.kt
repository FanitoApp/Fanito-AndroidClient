package io.android.fanito_androidclient.data.resource

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceHelper {

    fun getString(@StringRes resourceId: Int): String

    fun getDrawable(@DrawableRes resourceId: Int): Drawable?

}