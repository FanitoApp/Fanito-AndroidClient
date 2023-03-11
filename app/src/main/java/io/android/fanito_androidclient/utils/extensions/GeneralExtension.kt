package io.android.fanito_androidclient.utils.extensions

fun Number.toStringWithZeroPadding(): String {
  return String.format("%02d", this)
}