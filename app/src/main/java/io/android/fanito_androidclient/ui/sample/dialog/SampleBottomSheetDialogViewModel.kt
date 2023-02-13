package io.android.fanito_androidclient.ui.sample.dialog

import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SampleBottomSheetDialogViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<SampleBottomSheetDialogNavigator>(dataManager) {
}