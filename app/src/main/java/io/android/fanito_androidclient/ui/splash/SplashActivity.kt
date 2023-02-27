package io.fanito.android.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.fanito.android.data.local.prefs.AppPreferencesHelper
import io.fanito.android.ui.intro.IntroActivity
import io.fanito.android.ui.main.MainActivity
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