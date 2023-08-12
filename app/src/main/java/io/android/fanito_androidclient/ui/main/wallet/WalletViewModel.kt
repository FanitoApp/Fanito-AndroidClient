package io.android.fanito_androidclient.ui.main.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.PortfolioResponse
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.ui.main.wallet.txs_history.TxsHistoryDataSource
import io.android.fanito_androidclient.utils.NoInternetException
import io.android.fanito_androidclient.utils.enums.DataResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<WalletNavigator>(dataManager) {

  private val _userFullName = MutableLiveData<String>()
  val userFullName: LiveData<String> = _userFullName

  private val _tokens = MutableLiveData<DataResult>()
  val tokens: LiveData<DataResult> = _tokens

  private val _userBalance = MutableLiveData(0L)
  val userBalance: LiveData<Long> = _userBalance

  val txs = Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
    TxsHistoryDataSource(dataManager)
  }).flow.cachedIn(viewModelScope)

  init {
    fetchUserTokens()
    fetchUserFullName()
  }

  /**
   * Fetching the list of available tokens for the user from the server.
   */
  private fun fetchUserTokens() {
    val handler = CoroutineExceptionHandler { _, exception ->
      if (exception is NoInternetException) {
        _tokens.postValue(DataResult.Failure(dataManager.getString(R.string.network_error)))
      }
    }
    viewModelScope.launch(handler) {
      _tokens.postValue(DataResult.Loading(true))
      val result = dataManager.userPortfolio(dataManager.userId!!)
      when {
        // Error in retrieving the list of user tokens.
        !result.isSuccessful || result.body() == null ->
          _tokens.postValue(DataResult.Failure(dataManager.responseErrorMessage(result)))
        // The user doesn't have any tokens.
        result.body()!!.isEmpty() ->
          _tokens.postValue(DataResult.Failure(dataManager.getString(R.string.tokens_empty)))
        // Displaying the list of tokens.
        else -> {
          val tokens = arrayListOf<PortfolioResponse>()
          for (token in result.body()!!) {
            if (token.price == null || token.balance == null) continue
            // The IRR token corresponds to the user's balance in Iranian Rials (IRR) and doesn't need to be displayed in the list of tokens.
            if (token.symbol == "IRR") _userBalance.postValue(token.price * token.balance)
            else tokens.add(token)
          }
          _tokens.postValue(DataResult.Success(tokens))
        }
      }
    }
  }

  /**
   *Fetching the user's first name and last name.
   */
  private fun fetchUserFullName() {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      dataManager.userPersonal(dataManager.userId!!).run {
        if (!isSuccessful || body() == null) return@launch
        _userFullName.postValue("${body()!!.firstName!!} ${body()!!.lastName!!}")
      }
    }
  }

}