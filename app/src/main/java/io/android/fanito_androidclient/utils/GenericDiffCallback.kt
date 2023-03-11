package io.android.fanito_androidclient.utils

import androidx.recyclerview.widget.DiffUtil

class GenericDiffCallback<T : IComparable> : DiffUtil.ItemCallback<T>() {

  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return newItem.comparator() == oldItem.comparator()
  }

  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
    return newItem == oldItem
  }
}

interface IComparable {
  fun comparator(): Any?
  override fun equals(other: Any?): Boolean
}