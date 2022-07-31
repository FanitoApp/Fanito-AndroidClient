package io.android.fanito_androidclient.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.fanito.android.data.model.others.PhotoSlider
import io.fanito.android.databinding.ItemSliderBinding
import io.fanito.android.ui.base.BaseViewHolder


class PhotoSliderAdapter(
  private var items: List<PhotoSlider>,
) : RecyclerView.Adapter<PhotoSliderAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding =
      ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ItemViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
    items[position].let { viewHolder.onBind(it, position) }
  }

  override fun getItemCount(): Int = items.size

  class ItemViewHolder(private val binding: ItemSliderBinding) :
    BaseViewHolder<PhotoSlider>(binding.root) {

    override fun onBind(item: PhotoSlider, position: Int) {
      with(item) {
        binding.item = this
        binding.executePendingBindings()
      }
    }
  }
}