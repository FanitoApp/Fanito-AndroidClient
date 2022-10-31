package io.android.fanito_androidclient.ui.main.surveys.voting

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.android.fanito_androidclient.R
import io.android.fanito_androidclient.data.model.api.response.PollOptionsResponse
import io.android.fanito_androidclient.databinding.FragmentVotingBinding
import io.android.fanito_androidclient.ui.base.BaseFragment
import io.android.fanito_androidclient.utils.enums.DataResult
import io.android.fanito_androidclient.utils.extensions.gone
import io.android.fanito_androidclient.utils.extensions.visible

@AndroidEntryPoint
class VotingFragment : BaseFragment<FragmentVotingBinding, VotingViewModel?>(),
  VotingNavigator, OptionsAdapter.Listener {

  override val viewModel: VotingViewModel by viewModels()
  override val bindingVariable: Int get() = BR.viewModel
  override val layoutId: Int get() = R.layout.fragment_voting
  private val args: VotingFragmentArgs by navArgs()

  companion object {
    const val TAG = "VotingFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.setNavigator(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.setPoll(args.pollJson)
    setUp()
  }
  fun gotoInfoFragment()
    {
        viewModel.requiredTokenBalance.value?.let {
          viewModel.selectedToken?.userBalance=it
        }
        findNavController().navigate( VotingFragmentDirections.actionVotingToTokenInfo(viewModel.dataManager.objectToJson(
          viewModel.selectedToken!!
        )))


   }
  private fun setUp() {
    viewDataBinding?.let {
      it.back.setOnClickListener { findNavController().navigateUp() }

      it.clubIcon.setOnClickListener { gotoInfoFragment() }
      it.clubName.setOnClickListener { gotoInfoFragment() }
      val optionsAdapter = OptionsAdapter()
      optionsAdapter.setListener(this)
      it.options.adapter = optionsAdapter
      viewModel.options.observe(viewLifecycleOwner, { result ->
        it.options.gone()
        it.messageOptions.gone()
        it.loadingOptions.gone()
        when (result) {
          is DataResult.Loading -> it.loadingOptions.visible()
          is DataResult.Failure -> {
            it.messageOptions.visible()
            it.messageOptions.text = result.message
          }
          is DataResult.Success<*> -> {
            it.options.visible()
            optionsAdapter.submitList((result.data as List<PollOptionsResponse.Option>))
            optionsAdapter.notifyDataSetChanged()
          }
        }
      })
    }
  }

  override fun onOptionSelect(pos: Int) {
    // The user does not have the necessary token to participate in the survey
    if (!viewModel.hasRequiredToken) {
      toastMessage(R.string.required_token_not_found)
      return
    }
    // The user has already participated in the survey
    if (viewModel.alreadyVoted) {
      toastMessage(R.string.already_voted)
      return
    }
    // The survey has been locked due to a specific reason
    if (viewModel.isPollLocked.value == true) return

    viewModel.selectOptionAtPos(pos)
  }

  override fun onOptionVote(item: PollOptionsResponse.Option, pos: Int) {
    viewModel.showOptionLoadingAtPos(pos)
    viewModel.voteOption(item, pos)
  }

  override fun onStop() {
    viewModel.cancelTimer()
    super.onStop()
  }
}