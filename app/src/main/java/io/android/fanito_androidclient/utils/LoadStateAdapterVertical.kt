package io.android.fanito_androidclient.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.android.fanito_androidclient.databinding.ItemLoadingStateVerticalBinding
import io.android.fanito_androidclient.utils.extensions.changeVisibility

class LoadStateAdapterVertical(
  private val retry: () -> Unit
) : LoadStateAdapter<LoadStateAdapterVertical.ViewHolder>() {

  inner class ViewHolder(
    private val binding: ItemLoadingStateVerticalBinding,
    private val retry: () -> Unit
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {

      if (loadState is LoadState.Error) binding.textViewError.text =
        loadState.error.localizedMessage

      binding.progressbar.changeVisibility(loadState is LoadState.Loading)
      binding.buttonRetry.changeVisibility(loadState is LoadState.Error)
      binding.textViewError.changeVisibility(loadState is LoadState.Error)
      binding.buttonRetry.setOnClickListener { retry() }
      binding.progressbar.visibility = View.VISIBLE
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState
  ) = ViewHolder(
    ItemLoadingStateVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    retry
  )
}