package com.example.smalltalks.view.chat.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.domain.repository.local.Message

abstract class MultiViewHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    
    abstract fun bind(myId: String, message: com.example.domain.repository.local.Message)
}