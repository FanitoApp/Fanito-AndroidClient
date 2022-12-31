package io.android.fanito_androidclient.ui.main.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.android.fanito_androidclient.data.model.api.response.PortfolioResponse
import io.android.fanito_androidclient.databinding.ItemPortfolioBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class UserTokensAdapter :
  ListAdapter<PortfolioResponse, UserTokensAdapter.ViewHolder>(GenericDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = currentList.size

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    currentList[position]?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemPortfolioBinding) :
    BaseViewHolder<PortfolioResponse>(binding.root) {

    override fun onBind(item: PortfolioResponse, position: Int) {
      with(item) {
        binding.item = this
        binding.executePendingBindings()
      }
    }
  }
}