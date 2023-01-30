package io.android.fanito_androidclient.ui.main

import io.android.fanito_androidclient.ui.base.BaseNavigator

interface MainNavigator : BaseNavigator {

  /**
   *Directing the user to the initial page of the app
   */
  fun goToIntro()
}