package io.android.fanito_androidclient.ui.main.surveys.voting

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.data.model.api.response.PollOptionsResponse
import io.android.fanito_androidclient.data.model.api.response.UserDetailsResponse
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.utils.AppConstants.POLL_STATE_ENDED
import io.android.fanito_androidclient.utils.AppConstants.POLL_STATE_PENDING
import io.android.fanito_androidclient.utils.CommonUtils
import io.android.fanito_androidclient.utils.NoInternetException
import io.android.fanito_androidclient.utils.enums.DataResult
import io.android.fanito_androidclient.utils.extensions.toStringWithZeroPadding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<VotingNavigator>(dataManager) {

  private val _poll = MutableLiveData<GetPollsResponse.Poll>()
  val poll: LiveData<GetPollsResponse.Poll> = _poll

  // Survey creator information
  private val _pollIssuer = MutableLiveData<UserDetailsResponse>()
  val pollIssuer: LiveData<UserDetailsResponse> = _pollIssuer

  // Survey status (Waiting to Start, Active [with timer], Completed).
  private val _pollState = MutableLiveData<String>()
  val pollState: LiveData<String> = _pollState

  private val _options = MutableLiveData<DataResult>()
  val options: LiveData<DataResult> = _options

  // Is the survey eligible for voting or not?
  private val _isPollLocked = MutableLiveData(false)
  val isPollLocked: LiveData<Boolean> = _isPollLocked

  private var _timer: CountDownTimer? = null

  //Does the user have the required token to participate in the survey?
  var hasRequiredToken = false
    private set

  // The user's token balance for the required token in this survey
  private val _requiredTokenBalance = MutableLiveData(0L)
  val requiredTokenBalance: LiveData<Long> = _requiredTokenBalance

  // Has the user previously participated in this survey or not?
  var alreadyVoted = false
    private set

  fun setPoll(pollJson: String) {
    val poll = dataManager.jsonToObject(pollJson, GetPollsResponse.Poll::class.java)
    poll?.let {
      _poll.postValue(it)

      checkUserTokens(it.tokenSymbol)
      checkPollState(it)
      getPollIssuerDetails(it.issuerId)
      fetchPollOptions(it.id)
    }
  }
   var selectedToken : GetTokensResponse.Token? = null
  fun  getClubTokenFromListOfTokens(s: String?) {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      var resultBody = dataManager.getTokens(50, 0).body()
       selectedToken = resultBody!!.items!!.find { it ->
        it.title!!.contains(s?.removePrefix("باشگاه ").toString())
      }
    }
  }

  /**
   *Checking the user's available token list for participation in the survey
   *
   * @param pollTokenSymbol The symbol associated with the token for this survey
   */
  private fun checkUserTokens(pollTokenSymbol: String?) {
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      val result = dataManager.userPortfolio(dataManager.userId!!)
      if (!result.isSuccessful || result.body().isNullOrEmpty()) return@launch
      result.body()!!.forEach {
        // Checking the user's complete token list
        if (it.symbol == pollTokenSymbol) {
          // The number of tokens the user has of the required token
          it.balance?.let {
            _requiredTokenBalance.postValue(it)
          }
          hasRequiredToken = true
        }
      }
    }
  }

  /**
   *Fetching the creator information of this survey
   *
   * @param pollIssuerId The identifier of the survey creator
   */
  private fun getPollIssuerDetails(pollIssuerId: String?) {
    pollIssuerId?.let {
      val handler = CoroutineExceptionHandler { _, exception -> }
      viewModelScope.launch(handler) {
        val result = dataManager.userDetails(it)
        if (result.isSuccessful && result.body() != null)
          result.body()?.let {
            _pollIssuer.postValue (it)
            getClubTokenFromListOfTokens(it.name)
          }

          }

    }
  }

  /**
   *Checking the status of the survey
   */
  private fun checkPollState(poll: GetPollsResponse.Poll) {
    when (poll.status) {
      POLL_STATE_PENDING, POLL_STATE_ENDED -> {
        _pollState.postValue(dataManager.getString(poll.getStateTitle()))
        // Voting is only possible in active surveys
        _isPollLocked.postValue(true)
      }
      else -> {
        if (poll.endTime == null) return

        // When the survey is active and has a specified end time
        // Setting the timer for the end of the survey
        val endDate = CommonUtils.dateStringToDate(poll.endTime) ?: return
        initTimer(endDate)
      }
    }
  }

  /**
   *Setting up the timer for the end of the survey
   *
   * @param endDate The end date of the survey
   */
  private fun initTimer(endDate: Date) {
    val endDateInMillis = endDate.time
    val currentTimeInMillis = System.currentTimeMillis()
    // The survey has ended
    if (endDateInMillis <= currentTimeInMillis) return

    val remainingInMillis = endDateInMillis - currentTimeInMillis
    _timer = object : CountDownTimer(remainingInMillis, 1000) {
      override fun onTick(millisUntilFinished: Long) {
        val h = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        val m = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
        val s = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
        _pollState.postValue(
          "${h.toStringWithZeroPadding()}   :   ${m.toStringWithZeroPadding()}   :   ${s.toStringWithZeroPadding()}"
        )
      }

      override fun onFinish() {
        _pollState.postValue(dataManager.getString(R.string.ended))
        // The survey has ended, and voting is no longer possible
        _isPollLocked.postValue(true)
      }
    }
    _timer!!.start()
  }

  /**
   *Fetching the list of options related to the survey
   *
   * @param pollId The identifier of the survey
   */
  private fun fetchPollOptions(pollId: String?) {
    if (pollId.isNullOrEmpty()) return

    val handler = CoroutineExceptionHandler { _, exception ->
      if (exception is NoInternetException) {
        _options.postValue(DataResult.Failure(dataManager.getString(R.string.network_error)))
      }
    }
    viewModelScope.launch(handler) {
      _options.postValue(DataResult.Loading(true))
      val result = dataManager.pollOptions(pollId)
      when {
        // Error in retrieving the list of survey options
        !result.isSuccessful || result.body() == null ->
          _options.postValue(DataResult.Failure(dataManager.getString(R.string.get_poll_options_failed)))
        // The survey does not have any options
        result.body()!!.items == null || result.body()!!.items!!.isEmpty() ->
          _options.postValue(DataResult.Failure(dataManager.getString(R.string.poll_options_empty)))
        // Displaying the list of options
        else -> {
          _options.postValue(DataResult.Success(result.body()!!.items!!))
          // Checking the user's vote in this survey
          checkUserVote(pollId, result.body()!!.items!!)
        }
      }
    }
  }

  /**
   *Checking the user's vote in this survey
   *
   * @param pollId The identifier of this survey
   * @param options The options of the survey
   */
  private fun checkUserVote(pollId: String?, options: List<PollOptionsResponse.Option>) {
    if (pollId == null) return
    val handler = CoroutineExceptionHandler { _, _ -> }
    viewModelScope.launch(handler) {
      val result = dataManager.checkUserVote(pollId)
      if (!result.isSuccessful || result.body() == null) alreadyVoted = false
      else {
        // The user has already voted in this survey
        alreadyVoted = true
        // Marking the option that the user has previously voted for.
        options.forEachIndexed { index, option ->
          if (result.body()!!.optionId == option.id) {
            setOptionAsVotedAtPos(index)
            return@launch
          }
        }
      }
    }
  }

  /**
   *Selecting the desired option from the list
   *
   * @param pos The row number of the selected option
   */
  fun selectOptionAtPos(pos: Int) {
    if (_options.value is DataResult.Success<*>) {
      val data = (_options.value as DataResult.Success<*>).data as List<PollOptionsResponse.Option>
      data.forEachIndexed { index, option ->
        //The selected option should be marked as "selected"
        option.changeSelectState(index == pos)
        option.changeSendingState(false)
        option.changeVoteState(false)
      }
      _options.postValue(DataResult.Success(data))
    }
  }

  /**
   *Display the loading indicator for the option at the specified row number
   *
   * @param pos Row number of the selected option
   */
  fun showOptionLoadingAtPos(pos: Int) {
    if (_options.value is DataResult.Success<*>) {
      val data = (_options.value as DataResult.Success<*>).data as List<PollOptionsResponse.Option>
      data.forEachIndexed { index, option ->
        option.changeSelectState(false)
        option.changeVoteState(false)
        // The selected option should display a loading indicator.
        option.changeSendingState(pos == index)
      }
      _options.postValue(DataResult.Success(data))
    }
  }

  /**
   *Displaying an option provided at the specified row number
   *
   * @param pos The row number of the selected option
   */
  private fun setOptionAsVotedAtPos(pos: Int) {
    if (_options.value is DataResult.Success<*>) {
      val data = (_options.value as DataResult.Success<*>).data as List<PollOptionsResponse.Option>
      // Setting loading for the selected option
      data.forEachIndexed { index, option ->
        option.changeSelectState(false)
        // The selected option should be displayed as voted
        option.changeVoteState(pos == index)
        option.changeSendingState(false)
      }
      _options.postValue(DataResult.Success(data))
    }
  }

  /**
   *To vote for an option from the list
   *
   * @param option The selected option
   * @param pos The row number of the selected option
   */
  fun voteOption(option: PollOptionsResponse.Option, pos: Int) {
    val handler = CoroutineExceptionHandler { _, exception ->
      if (exception is NoInternetException) {
        _options.postValue(DataResult.Failure(dataManager.getString(R.string.network_error)))
        _isPollLocked.postValue(false)
        selectOptionAtPos(pos)
      }
    }

    viewModelScope.launch(handler) {
      // Submitting the user's vote to the server
      alreadyVoted = true
      val result =
        dataManager.registerVote(dataManager.userId!!, _poll.value!!.id!!, option.id, 100)

      if (result.isSuccessful) setOptionAsVotedAtPos(pos)
      else {
        alreadyVoted = false
        selectOptionAtPos(pos)
      }
    }
  }

  fun cancelTimer() = _timer?.cancel()
}