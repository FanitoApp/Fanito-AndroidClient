package io.android.fanito_androidclient.ui.main.surveys.voting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.android.fanito_androidclient.data.model.api.response.PollOptionsResponse
import io.android.fanito_androidclient.databinding.ItemSurveyOptionBinding
import io.android.fanito_androidclient.ui.base.BaseViewHolder
import io.android.fanito_androidclient.utils.GenericDiffCallback

class OptionsAdapter :
  ListAdapter<PollOptionsResponse.Option, OptionsAdapter.ViewHolder>(GenericDiffCallback()) {

  var listener: Listener? = null
    private set

  fun setListener(listener: Listener) {
    this.listener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding =
      ItemSurveyOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int = currentList.size

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    currentList[position]?.let { viewHolder.onBind(it, position) }
  }

  inner class ViewHolder(private val binding: ItemSurveyOptionBinding) :
    BaseViewHolder<PollOptionsResponse.Option>(binding.root) {

    override fun onBind(item: PollOptionsResponse.Option, position: Int) {
      with(item) {
        binding.item = this
        binding.vote.setOnClickListener { listener?.onOptionVote(this, position) }
        binding.root.setOnClickListener { listener?.onOptionSelect(position) }
        binding.executePendingBindings()
      }
    }
  }

  interface Listener {
    /**
     *Selecting an option
     *
     * @param pos The row number of the option
     */
    fun onOptionSelect(pos: Int)

    /**
     *Casting a vote for an option
     *
     * @param item The selected option
     */
    fun onOptionVote(item: PollOptionsResponse.Option, pos: Int)
  }
}