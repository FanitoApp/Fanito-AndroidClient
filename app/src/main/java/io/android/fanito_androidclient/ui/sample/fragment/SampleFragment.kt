package io.android.fanito_androidclient.ui.sample.fragment

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentSampleBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleFragment : BaseFragment<FragmentSampleBinding, SampleFragmentViewModel?>(),
  SampleFragmentNavigator {

  override val viewModel: SampleFragmentViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_sample

  companion object {
    const val TAG = "SampleFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }
}