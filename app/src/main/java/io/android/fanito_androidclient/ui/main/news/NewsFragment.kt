package io.android.fanito_androidclient.ui.main.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.FragmentNewsBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.ui.main.deatil.TokenDetailFragmentArgs
import io.android.fanito_androidclient.ui.main.home.token_info.TokenInfoFragmentDirections
import io.android.fanito_androidclient.ui.main.hunt.HuntViewModel

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel?>(),
    NewsNavigator {
    override val viewModel: NewsViewModel by viewModels()
    override val bindingVariable: Int get() = BR.viewModel
    override val layoutId: Int get() = R.layout.fragment_news
    private val args: NewsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        viewModel.init(args.newsToken)

    }
    private fun setUp() {
        viewDataBinding?.let {
            it.back.setOnClickListener { findNavController().navigateUp() }
        }
    }



}