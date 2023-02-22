package io.android.fanito_androidclient.ui.sample.fragment

import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SampleFragmentViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<SampleFragmentNavigator>(dataManager) {

}