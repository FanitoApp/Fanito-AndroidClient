package io.android.fanito_androidclient.ui.intro

import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<IntroNavigator>(dataManager) {
}