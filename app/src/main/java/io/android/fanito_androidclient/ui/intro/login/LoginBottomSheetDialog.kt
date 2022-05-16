package io.android.fanito_androidclient.ui.intro.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.DialogBottomSheetLoginBinding
import io.android.fanito_androidclient.ui.base.BaseBottomSheetDialog
import io.android.fanito_androidclient.ui.main.MainActivity

@AndroidEntryPoint
class LoginBottomSheetDialog :
  BaseBottomSheetDialog<DialogBottomSheetLoginBinding, LoginViewModel>(),
  LoginNavigator {

  override val viewModel: LoginViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.dialog_bottom_sheet_login

  companion object {
    const val TAG = "LoginBottomSheetDialog"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUp()
  }

  private fun setUp() {
  }

  override fun focusOnPhone() {
    Handler(Looper.getMainLooper()).postDelayed(
      { viewDataBinding?.phone?.requestFocus() }, 500
    )
  }

  override fun focusOnPin() {
    Handler(Looper.getMainLooper()).postDelayed(
      { viewDataBinding?.pin?.requestFocus() }, 500
    )
  }

  override fun goToHome() {
    startActivity(MainActivity.newIntent(requireActivity()))
    requireActivity().finish()
  }
}