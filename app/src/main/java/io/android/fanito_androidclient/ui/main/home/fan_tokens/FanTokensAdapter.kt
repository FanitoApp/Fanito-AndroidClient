package io.android.fanito_androidclient.ui.main.home.fan_tokens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.android.fanito_androidclient.data.model.api.response.GetTokensResponse
import io.android.fanito_androidclient.databinding.ItemFanTokenBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class FanTokensAdapter :
  PagingDataAdapter<GetTokensResponse.Token, FanTokensAdapter.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemFanTokenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    getItem(position)?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemFanTokenBinding) :
    BaseViewHolder<GetTokensResponse.Token>(binding.root) {

    override fun onBind(item: GetTokensResponse.Token, position: Int) {
      with(item) {
        binding.item = this
        binding.root.setOnClickListener { listener?.onTokenClick(this) }
        binding.executePendingBindings()
      }
    }

  }

  interface Listener {
    fun onTokenClick(item: GetTokensResponse.Token)
  }
}