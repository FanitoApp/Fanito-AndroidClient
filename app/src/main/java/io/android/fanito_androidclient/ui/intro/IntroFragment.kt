package io.android.fanito_androidclient.ui.intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentIntroBinding
import io.android.fanito_androidclient.ui.base.BaseFragment


@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel?>(), IntroNavigator {

  override val viewModel: IntroViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_intro

  companion object {
    const val TAG = "IntroFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUp()
  }

  private fun setUp() {
    viewDataBinding?.let {
      it.login.setOnClickListener {
        // Displaying the login sheet
        findNavController().navigate(R.id.action_introFragment_to_loginDialog)
      }
      it.register.setOnClickListener {
        // Redirecting to the fanito website for registration
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.fanito_website))))
      }
    }
  }
}