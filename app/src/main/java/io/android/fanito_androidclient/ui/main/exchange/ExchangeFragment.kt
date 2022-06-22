package io.android.fanito_androidclient.ui.main.exchange

import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentExchangeBinding
import io.android.fanito_androidclient.ui.base.BaseFragment

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding, ExchangeViewModel?>(),
  ExchangeNavigator {

  override val viewModel: ExchangeViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_exchange

  companion object {
    const val TAG = "ExchangeFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }
}