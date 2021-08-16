package com.example.smalltalks.view.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.smalltalks.databinding.MyChatItemBinding
import com.example.smalltalks.databinding.ReceiverChatItemBinding
import com.natife.example.domain.Message

class ChatAdapter(private val myId: String) :
    ListAdapter<Message, MultiViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        val binding = when (viewType) {
            MY_MSG_VIEW -> {
                MyChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            RECEIVER_MSG_VIEW -> {
                ReceiverChatItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> null
        }
        return ViewHolderFactory.create(binding)
    }

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(myId, message)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).from.id == myId)
            MY_MSG_VIEW
        else
            RECEIVER_MSG_VIEW
    }


    class MyViewHolder(private val binding: MyChatItemBinding) : MultiViewHolder(binding) {

        override fun bind(myId: String, message: Message) {
            binding.apply {
                chatUserName.text = message.from.name
                chatMessage.text = message.message
            }
        }
    }

    class ReceiverViewHolder(private val binding: ReceiverChatItemBinding) :
        MultiViewHolder(binding) {

        override fun bind(myId: String, message: Message) {
            binding.apply {
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
            return oldItem == newItem
        }
    }

    companion object {
        const val MY_MSG_VIEW = 0
        const val RECEIVER_MSG_VIEW = 1
    }
}