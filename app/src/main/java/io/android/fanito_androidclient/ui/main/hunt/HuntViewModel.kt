package io.android.fanito_androidclient.ui.main.hunt

import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HuntViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<HuntNavigator>(dataManager) {

}