package io.android.fanito_androidclient.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.BR
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.databinding.FragmentHomeBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.ui.main.MainActivity
import io.android.fanito_androidclient.ui.main.home.fan_tokens.FanTokensAdapter
import io.android.fanito_androidclient.utils.LoadStateAdapterHorizontal
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel?>(),
  HomeNavigator, FanTokensAdapter.Listener {

  override val viewModel: HomeViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_home

  private val handler = Handler(Looper.getMainLooper())

  companion object {
    const val TAG = "HomeFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpToolbar()
    setUpPhotoSlider()
    setUpFanTokens()
    setUpGamesAndRewards()
    setUpMarkets()
  }

  private fun setUpToolbar() {
    viewDataBinding?.scrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
    { _, _, _, _, _ -> viewModel.onTabSelect(null) })

    viewDataBinding?.includeTopToolbar?.let {
      it.profile.setOnClickListener { (requireActivity() as MainActivity).openDrawer() }
      it.news.setOnClickListener { focusOnNews() }
      it.fanClubs.setOnClickListener { focusOnFanTokens() }
      it.game.setOnClickListener { focusOnGamesAndRewards() }
      it.market.setOnClickListener { focusOnMarket() }
    }
  }

  private fun focusOnNews() {
    viewDataBinding?.let { it.scrollView.smoothScrollTo(0, it.photoSlider.top) }
    selectTabWithDelay(R.string.news)
  }

  private fun focusOnFanTokens() {
    viewDataBinding?.let { it.scrollView.smoothScrollTo(0, it.fanTokens.top+it.fanTokens.height/2) }
    selectTabWithDelay(R.string.fan_clubs)
  }

  private fun focusOnGamesAndRewards() {
    viewDataBinding?.let { it.scrollView.smoothScrollTo(0, it.gamesAndRewards.top+it.gamesAndRewards.height/2) }
    selectTabWithDelay(R.string.game_and_reward)
  }

  private fun focusOnMarket() {
    viewDataBinding?.let { it.scrollView.smoothScrollTo(0, it.markets.top+it.markets.height/2) }
    selectTabWithDelay(R.string.market)
  }

  private fun selectTabWithDelay(@StringRes tabTitleId: Int) {
    handler.postDelayed(
      { viewModel.onTabSelect(tabTitleId) },
      // Tab transitions to the selected state with a one-second delay, ensuring that scrolling does not cause the tab to exit the selected state.
      300,
    )
  }

  /**
     *Setting the content related to the main page slider.
   */
  private fun setUpPhotoSlider() {
    viewDataBinding?.let {
      val sliderItems = viewModel.getSliderItems()
      it.photoSlider.adapter = PhotoSliderAdapter(sliderItems)
      // Changing the direction of the image slider from right to left
      it.sliderIndicator.rotationY = 180F
      it.photoSlider.rotationY = 180F

      it.photoSlider.clipToPadding = false
      it.photoSlider.clipChildren = false
      it.photoSlider.offscreenPageLimit = 2
      it.photoSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

      it.photoSlider.setPageTransformer { page, position ->

        // Displaying a glimpse of the adjacent items in the image slider
        val offset = position * -(2 * 30 + 20)
        if (it.photoSlider.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
          if (ViewCompat.getLayoutDirection(it.photoSlider) == ViewCompat.LAYOUT_DIRECTION_RTL)
            page.translationX = -offset else page.translationX = offset
        } else page.translationY = offset
      }

      it.sliderIndicator.setViewPager2(it.photoSlider)
    }
  }

  /**
   *Setting the content of fan tokens.
   */
  private fun setUpFanTokens() {
    viewDataBinding?.let {
      val fanTokensAdapter = FanTokensAdapter()
      fanTokensAdapter.setListener(this)
      it.fanTokens.adapter = fanTokensAdapter.withLoadStateHeaderAndFooter(
        header = LoadStateAdapterHorizontal { fanTokensAdapter.retry() },
        footer = LoadStateAdapterHorizontal { fanTokensAdapter.retry() }
      )
      lifecycleScope.launch {
        viewModel.fanTokens.collectLatest { fanTokensAdapter.submitData(it) }
      }
    }
  }

  /**
   *Setting the content of games and prizes.
   */
  private fun setUpGamesAndRewards() {
    viewDataBinding?.let {
      val adapter = TemporaryHomeAdapters(2)
      it.gamesAndRewards.adapter = adapter
      viewModel.gamesAndRewards.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }
  }

  /**
   *Setting the content of the marketplace.
   */
  private fun setUpMarkets() {
    viewDataBinding?.let {
      val adapter = TemporaryHomeAdapters(3)
      it.markets.adapter = adapter
      viewModel.markets.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }
  }

  override fun onTokenClick(item: GetTokensResponse.Token) {
    // If the user's balance for this token is zero, they do not have the ability to view its information

//    if (viewModel._userBalance == 0L) toastMessage(getString(R.string.balance_is_not_enough))
    if (!item.symbol.equals("ESTG") && !item.symbol.equals("PRSP")) toastMessage(getString(R.string.balance_is_not_enough))
    else goToTokenInfo(viewModel.dataManager.objectToJson(item))
  }

  /**
   *Directing the user to the token information page.
   *
   * @param tokenJson JSON for the selected token.
   */
  private fun goToTokenInfo(tokenJson: String) {
    val action = HomeFragmentDirections.actionHomeToTokenInfo(tokenJson)
    findNavController().navigate(action)
  }
}