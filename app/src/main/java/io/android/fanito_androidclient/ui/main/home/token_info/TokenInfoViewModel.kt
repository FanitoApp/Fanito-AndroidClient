package io.android.fanito_androidclient.ui.main.home.token_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.UserDetailsResponse
import io.android.fanito_androidclient.data.model.others.StaticClubNews
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.ui.main.surveys.adapter.SurveysDataSource
import io.android.fanito_androidclient.utils.NoInternetException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenInfoViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<TokenInfoNavigator>(dataManager) {

  private val _token = MutableLiveData<GetTokensResponse.Token>()
  val token: LiveData<GetTokensResponse.Token> = _token

  // Token creator information
  private val _tokenIssuer = MutableLiveData<UserDetailsResponse>()
  val tokenIssuer: LiveData<UserDetailsResponse> = _tokenIssuer

  var surveys: Flow<PagingData<GetPollsResponse.Poll>>? = null

    // List of news related to the desired club
  private val _news = MutableLiveData<List<StaticClubNews>>()
  val news: LiveData<List<StaticClubNews>> = _news

  /**
   *Setting the selected token to retrieve its information.
   *
   * @param tokenJson Selected token JSON.

   */
  var tokensResponse: String=""
  fun setToken(tokenJson: String) {
    val token = dataManager.jsonToObject(tokenJson, GetTokensResponse.Token::class.java)
    tokensResponse=tokenJson
    token?.let { it ->
      _token.postValue(it)
      getTokenIssuerDetails(it.issuerId)
      initSurveysFlow(it.symbol)
      setStaticNews(it.symbol)
    }
  }

  /**
   *Fetching the list of surveys for this token
   *
   * @param tokenSymbol The abbreviation symbol for the token
   */
  private fun initSurveysFlow(tokenSymbol: String?) {
    tokenSymbol?.let { symbol ->
      surveys = Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
        SurveysDataSource(dataManager, symbol)
      }).flow.cachedIn(viewModelScope)
    }
  }

  /**
   *Setting the static news for displaying content relevant to the selected club
   *=== Temporarily until data is retrieved from the data web service ===
   *
   * @param tokenSymbol The abbreviation symbol for the desired club
   */
  private fun setStaticNews(tokenSymbol: String?) {
    tokenSymbol?.let {
      when (it) {
        "PRSP" -> _news.postValue(fakePrspNews())
        "ESTG" -> _news.postValue(fakeEstgNews())
        else -> _news.postValue(fakeOtherNews())
      }
    }
  }

  /**
   *Setting the static news related to Persepolis Club
   * === Temporarily ===
   */
  private fun fakePrspNews(): ArrayList<StaticClubNews> {
    val items = arrayListOf<StaticClubNews>()
    items.add(
      StaticClubNews(
        1,
        dataManager.getDrawable(R.drawable.perspollis),
        "واگذاری سهام پرسپولیس",
        dataManager.getString(R.string.perpolisNews1),
        R.drawable.perspollis
      )
    )
    items.add(
      StaticClubNews(
        2,
        dataManager.getDrawable(R.drawable.moshiri),
        "آیا مشیری مالک باشگاه پرسپولیس می شود؟",
        dataManager.getString(R.string.perpolisNews2),
        R.drawable.moshiri
      ,
      )
    )
    items.add(
      StaticClubNews(
        3,
        dataManager.getDrawable(R.drawable.alishah),
        "عالیشاه بعد از مصدومیت دوباره تمرین میکند",
        dataManager.getString(R.string.perpolisNews1)
      ,R.drawable.alishah
      )
    )
    return items
  }

  /**
   *Setting the static news related to Esteghlal Club
   * === Temporarily ===
   */
  private fun fakeEstgNews(): ArrayList<StaticClubNews> {
    val items = arrayListOf<StaticClubNews>()
    items.add(
      StaticClubNews(
        1,
        dataManager.getDrawable(R.drawable.esteglal),
        "واگذاری سهام استقلال",
        dataManager.getString(R.string.perpolisNews1),
        R.drawable.perspollis
      )
    )
    items.add(
      StaticClubNews(
        2,
        dataManager.getDrawable(R.drawable.veria),
        "وریا غفوری اخراج شد",
        dataManager.getString(R.string.esteglalNews2),
        R.drawable.veria
      )
    )
    items.add(
      StaticClubNews(
        3,
        dataManager.getDrawable(R.drawable.bella),
        "بلاتکلیفی مجیدی و استقلال مقابل فجر",
        dataManager.getString(R.string.esteglalNews3),
        R.drawable.bella
      )
    )
    return items
  }

  /**
   *Setting the static news related to other clubs
   * === Temporarily ===
   */
  private fun fakeOtherNews(): ArrayList<StaticClubNews> {
    val items = arrayListOf<StaticClubNews>()
    items.add(
      StaticClubNews(
        1,
        dataManager.getDrawable(R.drawable.breaking_news_1),
        "متن خبر اول باشگاه",
      )
    )
    items.add(
      StaticClubNews(
        2,
        dataManager.getDrawable(R.drawable.breaking_news_1),
        "متن خبر دوم باشگاه متن خبر دوم باشگاه",
      )
    )
    items.add(
      StaticClubNews(
        3,
        dataManager.getDrawable(R.drawable.breaking_news_1),
        "متن خبر سوم باشگاه متن خبر سوم باشگاه متن خبر سوم باشگاه",
      )
    )
    return items
  }

  /**
   *Fetching the creator information for this token
   *
   * @param tokenIssuerId The identifier of the token creator
   */
  lateinit var resultDate:UserDetailsResponse
  private fun getTokenIssuerDetails(tokenIssuerId: String?) {
    tokenIssuerId?.let { issuerId ->
      val handler = CoroutineExceptionHandler { _, exception ->
        if (exception is NoInternetException) {
          navigator?.toastMessage(R.string.network_error)
        }
      }
      viewModelScope.launch(handler) {
        val result = dataManager.userDetails(issuerId)
        if (result.isSuccessful && result.body() != null)
        {
          _tokenIssuer.postValue(result.body())
          resultDate= result.body()!!

        }
      }
    }
  }
}