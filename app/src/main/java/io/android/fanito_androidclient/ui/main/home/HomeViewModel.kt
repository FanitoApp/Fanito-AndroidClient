package io.android.fanito_androidclient.ui.main.home

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.BuildConfig.BASE_URL
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.others.FanClubToken
import io.android.fanito_androidclient.data.model.others.PhotoSlider
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.ui.main.home.fan_tokens.FanTokensDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<HomeNavigator>(dataManager) {

  private val _selectedTab = MutableLiveData<String?>(null)
  val selectedTab: LiveData<String?> = _selectedTab

  private val _gamesAndRewards = MutableLiveData<List<FanClubToken>>()
  val gamesAndRewards: LiveData<List<FanClubToken>> = _gamesAndRewards

  private val _markets = MutableLiveData<List<FanClubToken>>()
  val markets: LiveData<List<FanClubToken>> = _markets

  val fanTokens = Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
    FanTokensDataSource(dataManager)
  }).flow.cachedIn(viewModelScope)

  init {
    // Static and temporary items for the list of games and prizes
    _gamesAndRewards.postValue(fakeItems())
    // Static and temporary items for the list of markets
    _markets.postValue(fakeItems())
    getUserBalance()
  }
  var _userBalance :Long?=0L
  private fun getUserBalance()
  {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      val result = dataManager.userPortfolio(dataManager.userId!!)
      for (token in result.body()!!) {
        if (token.symbol == "IRR" && token.price != null && token.balance != null) {
          _userBalance = (token.price * token.balance)
          break
        }
      }
    }
  }
  /**
   *Items for the top slider on the homepage
   */
  fun getSliderItems(): ArrayList<PhotoSlider> {
    val sliderItems = arrayListOf<PhotoSlider>()
    sliderItems.add(
      PhotoSlider(
        1,
        "$BASE_URL:9000/public/3fa47b28-247b-4409-8fb2-95e3d364b19e.jpeg"
      )
    )
    sliderItems.add(
      PhotoSlider(
        2,
        "$BASE_URL:9000/public/3fa47b28-247b-4409-8fb2-95e3d3641e.jpeg"
      )
    )
    sliderItems.add(
      PhotoSlider(
        3,
        "$BASE_URL:9000/public/3fa47b28-247b-4409-8fb2-95e3d36421edfc.jpeg"
      )
    )
    sliderItems.add(
      PhotoSlider(
        4,
        "$BASE_URL:9000/public/3fa47b28-247b-4409-8fb2-95e3d36421edfcdrmj.jpeg"
      )
    )
    return sliderItems
  }

  /**
   *List of static items for display in the list
   * === Temporarily ===
   */
  private fun fakeItems(): ArrayList<FanClubToken> {
    val items = arrayListOf<FanClubToken>()
    items.add(
      FanClubToken(
        1,
        "برسبوليس",
        price = 13000,
        percent = -2.5f,
        participants = 135000
      )
    )
    items.add(
      FanClubToken(
        2,
        "استقلال",
        price = 5000,
        percent = 3.1f,
        participants = 15000
      )
    )
    items.add(
      FanClubToken(
        3,
        "ابومسلم",
        price = 13000,
        percent = -2.5f,
        participants = 135000
      )
    )
    return items
  }

  fun onTabSelect(@StringRes tabTitleId: Int?) {
    if (tabTitleId == null) _selectedTab.postValue(null)
    else _selectedTab.postValue(dataManager.getString(tabTitleId))
  }
}