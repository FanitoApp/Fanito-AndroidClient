package io.android.fanito_androidclient.ui.main.wallet.txs_history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetTxsResponse

class TxsHistoryDataSource(private val dataManager: DataManager) :
  PagingSource<Int, GetTxsResponse.Tx>() {

  companion object {
    const val LIMIT = 50
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetTxsResponse.Tx> {
    return try {
      val nextPageNumber = params.key ?: 0
      val response = dataManager.getTxs(dataManager.userId!!, LIMIT, nextPageNumber)
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

  override fun getRefreshKey(state: PagingState<Int, GetTxsResponse.Tx>): Int? {
    return null
  }

}