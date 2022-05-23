package io.android.fanito_androidclient.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.databinding.ActivityIntroBinding

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {

  private lateinit var binding: ActivityIntroBinding

  companion object {
    private const val TAG = "IntroActivity"
    fun newIntent(context: Context) = Intent(context, IntroActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityIntroBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}