package io.android.fanito_androidclient.ui.main.surveys.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse

class SurveysDataSource(
  private val dataManager: DataManager,
  private val tokenSymbol: String? = null
) :
  PagingSource<Int, GetPollsResponse.Poll>() {

  companion object {
    const val LIMIT = 50
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetPollsResponse.Poll> {
    return try {
      val nextPageNumber = params.key ?: 0
      val response = dataManager.getPolls(LIMIT, nextPageNumber, tokenSymbol)
      val totalCount = response.body()?.totalCount ?: 0

      LoadResult.Page(
        data = response.body()?.items ?: arrayListOf(),
        prevKey = if (nextPageNumber - LIMIT > 0) nextPageNumber - LIMIT else null,
        nextKey = if (totalCount >= LIMIT) nextPageNumber + LIMIT else null
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, GetPollsResponse.Poll>): Int? {
    return null
  }

}