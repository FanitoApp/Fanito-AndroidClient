package io.android.fanito_androidclient.ui.sample.dialog

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.DialogBottomSheetSampleBinding
import io.android.fanito_androidclient.ui.base.BaseBottomSheetDialog

@AndroidEntryPoint
class SampleBottomSheetDialog :
  BaseBottomSheetDialog<DialogBottomSheetSampleBinding, SampleBottomSheetDialogViewModel>(),
  SampleBottomSheetDialogNavigator {

  override val viewModel: SampleBottomSheetDialogViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.dialog_bottom_sheet_sample

  companion object {
    const val TAG = "SampleBottomSheetDialog"
    fun newInstance(): SampleBottomSheetDialog {
      val args = Bundle()
      val fragment = SampleBottomSheetDialog()
      fragment.arguments = args
      return fragment
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }
}