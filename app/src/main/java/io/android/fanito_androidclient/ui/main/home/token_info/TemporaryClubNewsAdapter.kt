package io.android.fanito_androidclient.ui.main.home.token_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.android.fanito_androidclient.data.model.api.response.NewsResponse
import io.android.fanito_androidclient.data.model.others.StaticClubNews
import io.android.fanito_androidclient.databinding.ItemClubNewsBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class TemporaryClubNewsAdapter :
  ListAdapter<StaticClubNews, TemporaryClubNewsAdapter.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemClubNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = currentList.size

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    currentList[position]?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemClubNewsBinding) :
    BaseViewHolder<StaticClubNews>(binding.root) {

    override fun onBind(item: StaticClubNews, position: Int) {
      with(item) {
        binding.item = this
        binding.root.setOnClickListener { listener?.onNewsClick(NewsResponse(item.title,item.descrption,"",item.drawableId,item.drawable)) }
        binding.executePendingBindings()
      }
    }

  }

  interface Listener {
    fun onNewsClick(item: NewsResponse)
  }
}