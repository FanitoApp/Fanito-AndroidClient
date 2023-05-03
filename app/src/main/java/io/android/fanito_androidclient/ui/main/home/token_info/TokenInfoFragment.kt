package io.android.fanito_androidclient.ui.main.home.token_info

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.data.model.api.response.NewsResponse
import io.android.fanito_androidclient.data.model.others.StaticClubNews
import io.android.fanito_androidclient.databinding.FragmentTokenInfoBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.utils.LoadStateAdapterHorizontal
import io.android.fanito_androidclient.ui.main.home.token_info.TokenInfoFragmentArgs
import io.android.fanito_androidclient.ui.main.home.token_info.TokenInfoFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TokenInfoFragment : BaseFragment<FragmentTokenInfoBinding, TokenInfoViewModel?>(),
  TokenInfoNavigator, TokenSurveysAdapter.Listener, TemporaryClubNewsAdapter.Listener {

  override val viewModel: TokenInfoViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_token_info
  private val args: TokenInfoFragmentArgs by navArgs()

  companion object {
    const val TAG = "TokenInfoFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.setToken(args.tokenJson)
    setUp()
    setUpSurveysSlider()
    setUpNewsSlider()
  }

  private fun setUp() {
    viewDataBinding?.let {
      it.back.setOnClickListener { findNavController().navigateUp() }
      it.wonder.setOnClickListener{findNavController().navigate(TokenInfoFragmentDirections.actionTokenInfoToDetail(viewModel.tokensResponse,viewModel.resultDate))}
    }
  }

  /**
   *Setting the content related to the survey sliders
   */
  private fun setUpSurveysSlider() {
    viewDataBinding?.let {
      val surveysAdapter = TokenSurveysAdapter()
      surveysAdapter.setListener(this)
      it.surveys.adapter = surveysAdapter.withLoadStateHeaderAndFooter(
        header = LoadStateAdapterHorizontal { surveysAdapter.retry() },
        footer = LoadStateAdapterHorizontal { surveysAdapter.retry() }
      )

      val pagerSnapHelper = PagerSnapHelper()
      pagerSnapHelper.attachToRecyclerView(it.surveys)

      lifecycleScope.launch {
        viewModel.surveys?.collectLatest { surveysAdapter.submitData(it) }
      }
    }
  }

  /**
   *Setting the content related to the news sliders.
   */
  private fun setUpNewsSlider() {
    viewDataBinding?.let {
      val newsAdapter = TemporaryClubNewsAdapter()
      newsAdapter.setListener(this)

      val pagerSnapHelper = PagerSnapHelper()
      pagerSnapHelper.attachToRecyclerView(it.news)

      it.news.adapter = newsAdapter
      viewModel.news.observe(viewLifecycleOwner, { news ->
        newsAdapter.submitList(news)
      })
    }
  }

  override fun onSurveyClick(item: GetPollsResponse.Poll) {
    val pollJson = viewModel.dataManager.objectToJson(item)
    // Directing the user to the voting page for the survey.
    val action = TokenInfoFragmentDirections.actionTokenInfoToVoting(pollJson)
    findNavController().navigate(action)
  }

  override fun onNewsClick(item: NewsResponse) {
//    item.content?.let { toastMessage(it) }

    var newsJson=viewModel.dataManager.objectToJson(item)
    val action =TokenInfoFragmentDirections.actionTokenInfoToNews(newsJson)
    findNavController().navigate(action)
  }
}