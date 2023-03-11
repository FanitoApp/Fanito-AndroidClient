package io.android.fanito_androidclient.utils

object AppConstants {
  const val DB_NAME = "fanito.db"
  const val PREF_NAME = "fanito_pref"
  const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"

  // Token symbol
  const val TOKEN_SYMBOL_RIAL = "IRR"

  // TX types
  const val TX_TYPE_WITHDRAW = "withdraw"
  const val TX_TYPE_DEPOSIT = "deposit"
  const val TX_TYPE_PURCHASE = "purchase"

  // TX states
  const val TX_STATE_SUCCESS = "success"
  const val TX_STATE_FAILED = "failed"
  const val TX_STATE_PENDING = "pending"

  // Poll states
  const val POLL_STATE_ACTIVE = "active"
  const val POLL_STATE_ENDED = "ended"
  const val POLL_STATE_PENDING = "pending"

  // Network Errors
  const val ERROR_CODE_REACH_LIMITATION = 40001
  const val ERROR_CODE_INVALID_OTP = 40304
  const val ERROR_CODE_INVALID_REQUEST_PARAMS = 40002
}