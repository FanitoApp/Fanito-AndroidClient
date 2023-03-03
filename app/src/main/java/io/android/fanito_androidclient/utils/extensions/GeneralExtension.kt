package io.fanito.android.utils.extensions

fun Number.toStringWithZeroPadding(): String {
  return String.format("%02d", this)
}