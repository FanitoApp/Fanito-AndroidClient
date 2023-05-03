package io.android.fanito_androidclient.ui.main.wallet.sample.activity

import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SampleActivityViewModel @Inject constructor(dataManager: DataManager) :
    BaseViewModel<SampleActivityNavigator>(dataManager) {
}