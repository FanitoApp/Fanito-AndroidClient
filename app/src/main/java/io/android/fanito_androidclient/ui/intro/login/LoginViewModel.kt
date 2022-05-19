package io.android.fanito_androidclient.ui.intro.login

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import dagger.hilt.android.lifecycle.HiltViewModel
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.DataManager
import io.android.fanito_androidclient.ui.base.BaseViewModel
import io.android.fanito_androidclient.utils.NoInternetException
import io.android.fanito_androidclient.utils.enums.Validation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(dataManager: DataManager) :
  BaseViewModel<LoginNavigator>(dataManager) {

  // The status of the field is currently changing
  val validation = ObservableField(Validation.INVALID)

  // The current stage in the login process
  val loginState = ObservableField(LoginState.PHONE)

  // The entered mobile number
  val phoneNumber = ObservableField<String>()

  // The verification code
  val verifyCode = ObservableField<String>()

  // The remaining time for verifying the one-time code (displayed)
  val remainingTimer = ObservableField("00:00")

  // Is it possible to resend the one-time verification code or not?
  val resendCodeEnable = ObservableBoolean(false)

  // The timer for the remaining time to verify the one-time code
  private var timer: Timer? = null

  // The identifier associated with the login process
  private var sessionId: String? = null

  // The expiration time of the one-time verification code in seconds
  private var otpExpiresSeconds: Long = 60

  private var handler = Handler(Looper.getMainLooper())

  fun onPhoneChanged(number: CharSequence, start: Int, before: Int, count: Int) {
    checkPhoneValidation(number)
  }

  /**
   *Validating the entered mobile number
   *
   * @param number Mobile number
   * @return The same and standard format according to the number entered by the user
   */
  private fun checkPhoneValidation(number: CharSequence) {
    var edited = number.toString()
    when {
      number.length == 13 && number.startsWith("+989") -> validation.set(Validation.VALID)
      number.length == 11 && number.startsWith("09") -> {
        validation.set(Validation.VALID)
        edited = number.toString().replaceFirst("09", "+989")
      }
      number.length == 10 && number.startsWith("9") -> {
        validation.set(Validation.VALID)
        edited = number.toString().replaceFirst("9", "+989")
      }
      else -> validation.set(Validation.INVALID)
    }
    handler.postDelayed({ phoneNumber.set(edited) }, 10)
  }

  fun onVerifyCodeChanged(code: CharSequence, start: Int, before: Int, count: Int) {
    checkOtpValidation(code)
  }

  /**
     *Checking the initial validity of the one-time code
   *
   * @param code Code entered
   */
  private fun checkOtpValidation(code: CharSequence) {
    validation.set(if (code.length == 4) Validation.VALID else Validation.INVALID)
  }

  fun onNextClick(v: View?) {
    when {
      loginState.get() == LoginState.PHONE && validation.get() == Validation.VALID -> sendOtp()
      loginState.get() == LoginState.VERIFY -> verifyOtpAndLogin()
    }
  }

  /**
   *Request to send a one-time code
   */
  private fun sendOtp() {
    val handler = CoroutineExceptionHandler { _, exception ->
      if (exception is NoInternetException) {
        loginState.set(LoginState.PHONE)
        navigator?.toastMessage(R.string.network_error)
      }
    }

    loginState.set(LoginState.LOADING)
    viewModelScope.launch(handler) {
      val result = dataManager.sendOtp(phoneNumber.get()!!)
      when {
        result.isSuccessful && result.body() != null -> {
          sessionId = result.body()!!.id
          otpExpiresSeconds = result.body()!!.expiresAt
          goToVerifyState()
        }
        else -> {
          loginState.set(LoginState.PHONE)
          navigator?.toastMessage(dataManager.responseErrorMessage(result))
        }
      }
    }
  }

  /**
   *Go to the one-time code verification stage
   */
  private fun goToVerifyState() {
    loginState.set(LoginState.VERIFY)
    validation.set(Validation.INVALID)
    navigator?.focusOnPin()
    resetTimer()
    startTimer()
  }

  /**
   *One-time code confirmation and login request
   */
  private fun verifyOtpAndLogin() {
    val handler = CoroutineExceptionHandler { _, exception ->
      if (exception is NoInternetException) {
        loginState.set(LoginState.VERIFY)
        navigator?.toastMessage(R.string.network_error)
      }
    }
    navigator?.hideKeyboard()
    loginState.set(LoginState.LOADING)
    viewModelScope.launch(handler) {
      when (verifyOtp()) {
        true -> login()
        false -> {
          navigator?.toastMessage(R.string.phone_not_registered)
          navigator?.back()
        }
        else -> {
        }
      }
    }
  }

  /**
   *Verification of one-time code
   */
  private suspend fun verifyOtp(): Boolean? {
    loginState.set(LoginState.LOADING)
    val result = dataManager.verifyOtp(phoneNumber.get()!!, verifyCode.get()!!, sessionId!!)
    return when {
      result.isSuccessful && result.body() != null -> {
        resetTimer()
        sessionId = result.body()!!.sessionId
        result.body()!!.registered
      }
      else -> {
        verifyCode.set("")
        loginState.set(LoginState.VERIFY)
        navigator?.toastMessage(dataManager.responseErrorMessage(result))
        null
      }
    }
  }

  /**
   *Verification of one-time code
   */
  private suspend fun login() {
    loginState.set(LoginState.LOADING)
    val result = dataManager.login(sessionId!!)
    when {
      result.isSuccessful && result.body() != null -> {
        val token = result.body()!!.accessToken
        dataManager.refreshToken = result.body()!!.refreshToken
        dataManager.accessToken = token
        val jwt = JWT(token)
        dataManager.userId = jwt.subject
        navigator?.goToHome()
      }
      else -> {
        onEditClick()
        navigator?.toastMessage(dataManager.responseErrorMessage(result))
      }
    }
  }

  private fun startTimer() {
    timer!!.schedule(object : TimerTask() {
      override fun run() {
        if (otpExpiresSeconds == 0L) {
          remainingTimer.set(dataManager.getString(R.string.resend_code))
          resendCodeEnable.set(true)
          timer?.cancel()
        } else {
          otpExpiresSeconds--
          remainingTimer.set(secToTimerString(otpExpiresSeconds))
        }
      }
    }, 0, 1000)
  }

  /**
   *
  Convert seconds to displayable timer (minutes and seconds)
   *
   * @param seconds Duration in seconds
   */
  private fun secToTimerString(seconds: Long): String {
    val min = TimeUnit.SECONDS.toMinutes(seconds)
    val sec = (seconds % 60).toInt()
    return if (sec < 10) "$min:0$sec" else "$min:$sec"
  }

  fun onEditClick(v: View? = null) {
    editPhoneNumber()
  }

  /**
   *Edit the entered mobile number
   */
  private fun editPhoneNumber() {
    resetTimer()
    phoneNumber.set("")
    verifyCode.set("")
    loginState.set(LoginState.PHONE)
    navigator?.focusOnPhone()
  }

  /**
   *Restart the timer
   */
  private fun resetTimer() {
    timer?.cancel()
    timer = Timer()
    resendCodeEnable.set(false)
    remainingTimer.set("00:00")
  }

  fun onResendClick(v: View?) {
    resetTimer()
    startTimer()
  }

  enum class LoginState {
    PHONE, VERIFY, LOADING
  }
}