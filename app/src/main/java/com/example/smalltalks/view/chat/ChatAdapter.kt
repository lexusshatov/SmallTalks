package com.example.smalltalks.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalks.databinding.ChatItemBinding

class ChatAdapter(messageList: List<MessageItem> = emptyList()) : ListAdapter<MessageItem, ChatAdapter.ViewHolder>(UserDiffCallback()) {
    init {
        submitList(messageList)
    }

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

        fun bind(messageItem: MessageItem) {
            binding.apply {
                if (messageItem.fromMe) {
                    //TODO set gravity layout to end
                }
                chatUserName.text = messageItem.messageDto.from.name
                chatMessage.text = messageItem.messageDto.message
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return false
        }
    }

    fun add(messageItem: MessageItem) {
        submitList(currentList + messageItem)
    }
}