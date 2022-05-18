package io.android.fanito_androidclient.ui.intro.login

import io.android.fanito_androidclient.ui.base.BaseNavigator

interface LoginNavigator : BaseNavigator {

  fun focusOnPhone()

  fun focusOnPin()

  fun goToHome()
}