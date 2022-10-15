package io.android.fanito_androidclient.ui.main.surveys.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import io.android.fanito_androidclient.data.model.api.response.GetPollsResponse
import io.android.fanito_androidclient.databinding.ItemSurveyBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.AppConstants.POLL_STATE_PENDING
import io.android.fanito_androidclient.utils.GenericDiffCallback
import io.android.fanito_androidclient.utils.extensions.gradient

class SurveysAdapter :
  PagingDataAdapter<GetPollsResponse.Poll, SurveysAdapter.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemSurveyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    getItem(position)?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemSurveyBinding) :
    BaseViewHolder<GetPollsResponse.Poll>(binding.root) {

    override fun onBind(item: GetPollsResponse.Poll, position: Int) {
      val c = itemView.context
      with(item) {
        binding.item = this
        binding.state.text = c.getString(item.getStateTitle())
        if (item.status == POLL_STATE_PENDING) binding.state.gradient(true)
        else binding.state.setTextColor(ContextCompat.getColor(c, item.getStateColor()))
        binding.root.setOnClickListener { listener?.onItemSelect(this) }
        binding.executePendingBindings()
      }
    }

  }

  interface Listener {
    fun onItemSelect(item: GetPollsResponse.Poll)
  }
}