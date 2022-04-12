package io.android.fanito_androidclient.data.resource

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppResourceHelper @Inject constructor(private val context: Context) : ResourceHelper {

    override fun getString(resourceId: Int): String = context.getString(resourceId)

    override fun getDrawable(@DrawableRes resourceId: Int) =
        ContextCompat.getDrawable(context, resourceId)
}