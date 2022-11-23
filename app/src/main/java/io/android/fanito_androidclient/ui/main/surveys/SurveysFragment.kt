package io.android.fanito_androidclient.ui.main.surveys

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.databinding.FragmentSurveysBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.ui.main.surveys.adapter.SurveysAdapter
import io.android.fanito_androidclient.utils.LoadStateAdapterVertical
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SurveysFragment : BaseFragment<FragmentSurveysBinding, SurveysViewModel?>(),
  SurveysNavigator, SurveysAdapter.Listener {

  override val viewModel: SurveysViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_surveys

  companion object {
    const val TAG = "SurveysFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUp()
  }

  private fun setUp() {
    setUpSurveys()
    viewDataBinding?.let {
      it.back.setOnClickListener { findNavController().navigateUp() }
    }
  }

  private fun setUpSurveys() {
    viewDataBinding?.let {
      val surveysAdapter = SurveysAdapter()
      surveysAdapter.setListener(this)
      it.surveys.adapter = surveysAdapter.withLoadStateHeaderAndFooter(
        header = LoadStateAdapterVertical { surveysAdapter.retry() },
        footer = LoadStateAdapterVertical { surveysAdapter.retry() },
      )
      lifecycleScope.launch {
        viewModel.surveys.collectLatest { surveysAdapter.submitData(it) }
      }
    }
  }

  override fun onItemSelect(item: GetPollsResponse.Poll) {
    val pollJson = viewModel.dataManager.objectToJson(item)
    val action = SurveysFragmentDirections.actionSurveysToVoting(pollJson)
    findNavController().navigate(action)
  }
}