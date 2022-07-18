package io.android.fanito_androidclient.ui.main.home.token_info

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.databinding.ItemTokenSurveyBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.AppConstants
import io.android.fanito_androidclient.utils.GenericDiffCallback
import io.android.fanito_androidclient.utils.extensions.gradient

class TokenSurveysAdapter :
  PagingDataAdapter<GetPollsResponse.Poll, TokenSurveysAdapter.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemTokenSurveyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    getItem(position)?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemTokenSurveyBinding) :
    BaseViewHolder<GetPollsResponse.Poll>(binding.root) {

    override fun onBind(item: GetPollsResponse.Poll, position: Int) {
      val c = itemView.context
      with(item) {
        binding.item = this
        binding.state.text = c.getString(item.getStateTitle())
        if (item.status == AppConstants.POLL_STATE_PENDING) binding.state.gradient(true)
        else binding.state.setTextColor(ContextCompat.getColor(c, item.getStateColor()))
        binding.description.movementMethod = ScrollingMovementMethod()
        binding.description.setOnClickListener { listener?.onSurveyClick(this) }
        binding.root.setOnClickListener { listener?.onSurveyClick(this) }
        binding.executePendingBindings()
      }
    }
  }

  interface Listener {
    fun onSurveyClick(item: GetPollsResponse.Poll)
  }
}