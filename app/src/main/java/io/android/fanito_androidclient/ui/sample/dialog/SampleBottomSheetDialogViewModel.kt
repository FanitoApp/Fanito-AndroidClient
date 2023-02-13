package io.fanito.android.ui.sample.dialog

import dagger.hilt.android.lifecycle.HiltViewModel
import io.fanito.android.data.DataManager
import io.fanito.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SampleBottomSheetDialogViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<SampleBottomSheetDialogNavigator>(dataManager) {
}