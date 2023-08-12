package io.android.fanito_androidclient.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.UserPersonalResponse
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.utils.AppConstants.TOKEN_SYMBOL_RIAL
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<MainNavigator>(dataManager) {

  private val _userPersonal = MutableLiveData<UserPersonalResponse>()
  val userPersonal: LiveData<UserPersonalResponse> = _userPersonal

  private val _userTokensCount = MutableLiveData(0L)
  val userTokensCount: LiveData<Long> = _userTokensCount

  init {
    fetchUserFullName()
    fetchUserTokens()
  }

  /**
   *Retrieving the user's first name and last name.
   */
  private fun fetchUserFullName() {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      dataManager.userPersonal(dataManager.userId!!).run {
        if (isSuccessful && body() != null) _userPersonal.postValue(body())
      }
    }
  }

  /**
   * Fetching the list of user fan tokens.
   */
  private fun fetchUserTokens() {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      val result = dataManager.userPortfolio(dataManager.userId!!)
      if (!result.isSuccessful || result.body().isNullOrEmpty()) return@launch

      // Summing up the user's fan tokens.
      var tokens = 0L
      result.body()!!.forEach {
        // Cash balance is not considered as part of the tokens.

        if (it.symbol != TOKEN_SYMBOL_RIAL && it.balance != null) tokens += it.balance
      }
      _userTokensCount.postValue(tokens)
    }
  }

  fun signOut() {
    dataManager.clearUserData()
    navigator?.goToIntro()
  }
}