package com.example.smalltalks.view.chat.adapter

import androidx.viewbinding.ViewBinding
import com.example.smalltalks.databinding.MyChatItemBinding
import com.example.smalltalks.databinding.ReceiverChatItemBinding

object ViewHolderFactory {

    fun create(binding: ViewBinding?): MultiViewHolder? =
        when (binding) {
            is MyChatItemBinding -> ChatAdapter.MyViewHolder(binding)
            is ReceiverChatItemBinding -> ChatAdapter.ReceiverViewHolder(binding)
            else -> null
        }
}