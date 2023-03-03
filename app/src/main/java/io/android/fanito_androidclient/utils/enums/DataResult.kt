package io.android.fanito_androidclient.utils.enums

sealed class DataResult {
  data class Loading(var loading: Boolean) : DataResult()
  data class Success<T>(var data: T) : DataResult()
  data class Failure(var message: String, var code: Int? = null) : DataResult()
}