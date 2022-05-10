package io.android.fanito_androidclient.ui.base

import androidx.annotation.StringRes

interface BaseNavigator {

  fun back()

  fun hideKeyboard()

  fun toastMessage(@StringRes messageId: Int)

  fun toastMessage(message: String)
}