package com.example.smalltalks.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalks.databinding.ChatItemBinding
import com.example.smalltalks.model.remote_protocol.MessageDto

class ChatAdapter : ListAdapter<MessageDto, ChatAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    override fun getItemCount() = currentList.size


    class ViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(messageDto: MessageDto) {
            binding.apply {
                chatUserName.text = messageDto.from.name
                chatMessageContainer.text = messageDto.message
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<MessageDto>() {
        override fun areItemsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MessageDto, newItem: MessageDto): Boolean {
            return oldItem == newItem
        }
    }

    fun add(messageDto: MessageDto) {
        if (!currentList.contains(messageDto)) {
            submitList(currentList + messageDto)
        }
    }
}