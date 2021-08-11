package com.example.smalltalks.view.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalks.databinding.ListItemBinding
import com.example.domain.remote_protocol.User

class UserListAdapter(
    private val onClick: (com.example.domain.remote_protocol.User) -> Unit
) : ListAdapter<com.example.domain.remote_protocol.User, UserListAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onClick)
    }


    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: com.example.domain.remote_protocol.User, onClick: (com.example.domain.remote_protocol.User) -> Unit) {
            binding.apply {
                itemContainer.text = user.name
                itemContainer.setOnClickListener {
                    onClick(user)
                }
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<com.example.domain.remote_protocol.User>() {
        override fun areItemsTheSame(oldItem: com.example.domain.remote_protocol.User, newItem: com.example.domain.remote_protocol.User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: com.example.domain.remote_protocol.User, newItem: com.example.domain.remote_protocol.User): Boolean {
            return oldItem.name == newItem.name
        }
    }
}