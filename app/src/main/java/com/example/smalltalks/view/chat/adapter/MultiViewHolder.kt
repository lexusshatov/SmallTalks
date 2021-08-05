package com.example.smalltalks.view.chat.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.smalltalks.model.repository.local.Message

abstract class MultiViewHolder(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    
    abstract fun bind(myId: String, message: Message)
}