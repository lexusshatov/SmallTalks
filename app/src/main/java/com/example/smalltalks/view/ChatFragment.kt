package com.example.smalltalks.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentChatBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.ChatViewModel

class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>() {

    override val viewModel by viewModels<ChatViewModel>()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentChatBinding =
        { inflater, container ->
            FragmentChatBinding.inflate(inflater, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO
    }

    companion object {

        fun newInstance() = ChatFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_list -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, UserListFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }
        return true
    }
}