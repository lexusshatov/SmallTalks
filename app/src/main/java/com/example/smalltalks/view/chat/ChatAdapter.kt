package com.example.smalltalks.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalks.databinding.ChatItemBinding
import com.example.smalltalks.model.repository.local.Message

class ChatAdapter(private val meId: String, messageList: List<Message> = emptyList()) :
    ListAdapter<Message, ChatAdapter.ViewHolder>(UserDiffCallback()) {
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
        holder.bind(meId, message)
    }

    override fun getItemCount() = currentList.size


    class ViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meId: String, message: Message) {
            binding.apply {
                if (message.from.id == meId) {
                    //TODO set gravity layout to end
                }
                chatUserName.text = message.from.name
                chatMessage.text = message.message
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return false
        }
    }

    //TODO maybe delete
    fun add(messageItem: Message) {
        submitList(currentList + messageItem)
    }
}