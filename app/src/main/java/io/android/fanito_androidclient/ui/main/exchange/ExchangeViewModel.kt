package io.android.fanito_androidclient.ui.main.exchange

import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<ExchangeNavigator>(dataManager) {

}