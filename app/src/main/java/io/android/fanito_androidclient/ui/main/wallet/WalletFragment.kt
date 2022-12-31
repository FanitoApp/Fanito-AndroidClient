package io.android.fanito_androidclient.ui.main.wallet

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.model.api.response.PortfolioResponse
import io.android.fanito_androidclient.databinding.FragmentWalletBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.ui.main.MainActivity
import io.android.fanito_androidclient.ui.main.wallet.txs_history.TxsHistoryAdapter
import io.android.fanito_androidclient.utils.LoadStateAdapterVertical
import io.android.fanito_androidclient.utils.enums.DataResult
import io.android.fanito_androidclient.utils.extensions.gone
import io.android.fanito_androidclient.utils.extensions.visible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding, WalletViewModel?>(),
  WalletNavigator {

  override val viewModel: WalletViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_wallet

  companion object {
    const val TAG = "WalletFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUp()
    setUpTokens()
    setUpHistory()
  }

  private fun setUp() {
    viewDataBinding?.let {
      it.profileWallet.setOnClickListener { (requireActivity() as MainActivity).openDrawer() }
    }
  }

  /**
   *Handling the display of the user's token list
   */
  private fun setUpTokens() {
    viewDataBinding?.let {
      val tokensAdapter = UserTokensAdapter()
      it.userTokens.adapter = tokensAdapter
      viewModel.tokens.observe(viewLifecycleOwner, { result ->
        it.loadingTokens.gone()
        it.userTokens.gone()
        it.userTokensMessage.gone()
        when (result) {
          is DataResult.Loading -> it.loadingTokens.visible()
          is DataResult.Failure -> {
            it.userTokensMessage.visible()
            it.userTokensMessage.text = result.message
          }
          is DataResult.Success<*> -> {
            it.userTokens.visible()
            tokensAdapter.submitList((result.data as List<PortfolioResponse>))
          }
        }
      })
    }
  }

  /**
   *Handling the display of the user's transaction list
   */
  private fun setUpHistory() {
    viewDataBinding?.let {
      val txsAdapter = TxsHistoryAdapter()
      it.history.apply {
        adapter = txsAdapter.withLoadStateHeaderAndFooter(
          header = LoadStateAdapterVertical { txsAdapter.retry() },
          footer = LoadStateAdapterVertical { txsAdapter.retry() }
        )
      }
      lifecycleScope.launch {
        viewModel.txs.collectLatest { txsAdapter.submitData(it) }
      }
    }
  }
}