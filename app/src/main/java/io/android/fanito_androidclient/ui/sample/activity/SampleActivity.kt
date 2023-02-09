package io.android.fanito_androidclient.ui.sample.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.databinding.ActivitySampleBinding
import io.android.fanito_androidclient.ui.base.BaseActivity

@AndroidEntryPoint
class SampleActivity : BaseActivity<ActivitySampleBinding, SampleActivityViewModel>(),
  SampleActivityNavigator {

  override val viewModel: SampleActivityViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.activity_sample

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  companion object {
    private const val TAG = "SampleActivity"
    fun newIntent(context: Context) = Intent(context, SampleActivity::class.java)
  }
}