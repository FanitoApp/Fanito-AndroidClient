package io.android.fanito_androidclient.ui.main.wallet.txs_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import io.android.fanito_androidclient.data.model.api.response.GetTxsResponse
import io.android.fanito_androidclient.databinding.ItemTxHistoryBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class TxsHistoryAdapter :
  PagingDataAdapter<GetTxsResponse.Tx, TxsHistoryAdapter.ViewHolder>(GenericDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemTxHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    getItem(position)?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemTxHistoryBinding) :
    BaseViewHolder<GetTxsResponse.Tx>(binding.root) {

    override fun onBind(item: GetTxsResponse.Tx, position: Int) {
      val c = itemView.context
      with(item) {
        binding.item = this
        binding.type.text = c.getString(item.getTypeTitle())
        binding.type.setTextColor(ContextCompat.getColor(c, item.getTypeColor()))
        binding.imgType.setImageResource(item.getTypeIcon())
        binding.executePendingBindings()
      }
    }
  }
}