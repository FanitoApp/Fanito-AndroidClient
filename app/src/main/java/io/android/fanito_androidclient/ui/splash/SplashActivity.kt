package io.android.fanito_androidclient.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.data.local.prefs.AppPreferencesHelper
import io.android.fanito_androidclient.ui.intro.IntroActivity
import io.android.fanito_androidclient.ui.main.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

  @Inject
  lateinit var sharedPrefs: AppPreferencesHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Handler(mainLooper).postDelayed({
      // Start activity
      if (sharedPrefs.accessToken.isNullOrEmpty())
        startActivity(IntroActivity.newIntent(this))
      else startActivity(MainActivity.newIntent(this))
      // close splash activity
      this.finish()
    }, 1000)
  }
}