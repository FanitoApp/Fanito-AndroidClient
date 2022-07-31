package io.android.fanito_androidclient.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.android.fanito_androidclient.data.model.others.FanClubToken
import io.android.fanito_androidclient.databinding.ItemTemporaryHomeBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class TemporaryHomeAdapters(private val type: Int) :
  ListAdapter<FanClubToken, TemporaryHomeAdapters.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemTemporaryHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = currentList.size

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    currentList[position]?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemTemporaryHomeBinding) :
    BaseViewHolder<FanClubToken>(binding.root) {

    override fun onBind(item: FanClubToken, position: Int) {
      with(item) {
        binding.item = this
        binding.type = type
        binding.root.setOnClickListener { listener?.onItemClick(this) }
        binding.executePendingBindings()
      }
    }

  }

  interface Listener {
    fun onItemClick(item: FanClubToken)
  }
}