package com.example.smalltalks.view.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalks.R
import com.example.smalltalks.databinding.ListItemBinding
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.view.chat.ChatFragment

class UserListAdapter(
    private val fragmentManager: FragmentManager
) : ListAdapter<User, UserListAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, fragmentManager)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun getItemCount() = currentList.size


    class ViewHolder(private val binding: ListItemBinding, private val fragmentManager: FragmentManager) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                itemContainer.text = user.name
                itemContainer.setOnClickListener{
                    fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, ChatFragment(user))
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }
    }
}