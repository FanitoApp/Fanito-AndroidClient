package io.android.fanito_androidclient.ui.main.deatil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.BR
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentTokenDetailBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
@AndroidEntryPoint
class TokenDetailFragment: BaseFragment<FragmentTokenDetailBinding, TokenDetailViewModel?>(),
    TokenDetailNavigator {

    override val viewModel: TokenDetailViewModel by viewModels()
    override val bindingVariable: Int  get() = BR.viewModel
    override val layoutId: Int  get() =R.layout.fragment_token_detail
    private val args: TokenDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.tokenJson,args.issuerJson)
        setUp()
    }

    private fun setUp() {
        viewDataBinding?.let {
            it.back.setOnClickListener{findNavController().navigateUp()}
        }
    }
}