package io.android.fanito_androidclient.ui.main.home.fan_tokens

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse

class FanTokensDataSource(private val dataManager: DataManager) :
  PagingSource<Int, GetTokensResponse.Token>() {

  companion object {
    const val LIMIT = 50
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetTokensResponse.Token> {
    return try {
      val nextPageNumber = params.key ?: 0
      val response = dataManager.getTokens(LIMIT, nextPageNumber)
      val totalCount = response.body()?.totalCount ?: 0
      val items = response.body()?.items ?: arrayListOf()

      checkUserBalances(items)
      getTokenIssuerDetails(items)

      LoadResult.Page(
        data = items,
        prevKey = if (nextPageNumber - LIMIT > 0) nextPageNumber - LIMIT else null,
        nextKey = if (totalCount >= LIMIT) nextPageNumber + LIMIT else null
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  /**
   *Setting user balance from each fan token
   */
  private suspend fun checkUserBalances(items: List<GetTokensResponse.Token>) {
    val userPortfolioResult = dataManager.userPortfolio(dataManager.userId!!)
    if (userPortfolioResult.isSuccessful && userPortfolioResult.body() != null) {
      items.forEach { token ->
        userPortfolioResult.body()!!.forEach { portfolio ->
          if (portfolio.symbol == token.symbol) token.userBalance = portfolio.balance ?: 0
        }
      }
    }
  }

  /**
   *To obtain the image associated with each fan token from the profile of its issuer.
   */
  private suspend fun getTokenIssuerDetails(items: List<GetTokensResponse.Token>) {
    items.forEach { token ->
      val result = dataManager.userDetails(token.issuerId!!)
      if (result.isSuccessful && result.body() != null) token.imageURL = result.body()!!.pictureURL
    }
  }

  override fun getRefreshKey(state: PagingState<Int, GetTokensResponse.Token>): Int? {
    return null
  }

}