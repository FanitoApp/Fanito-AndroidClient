package io.android.fanito_androidclient.ui.main.hunt

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentHuntBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HuntFragment : BaseFragment<FragmentHuntBinding, HuntViewModel?>(),
  HuntNavigator {

  override val viewModel: HuntViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_hunt

  companion object {
    const val TAG = "HuntFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }
}