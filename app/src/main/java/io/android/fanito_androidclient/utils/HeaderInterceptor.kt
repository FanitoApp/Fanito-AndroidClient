package io.android.fanito_androidclient.utils

import io.android.fanito_androidclient.data.local.prefs.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(
  private val preferencesHelper: PreferencesHelper
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response = chain.run {
    val token = preferencesHelper.accessToken
    if (token.isNullOrEmpty()) proceed(request())
    else proceed(
      request()
        .newBuilder()
        .addHeader("Authorization", "Bearer $token")
        .build()
    )
  }

}