package com.example.smalltalks.view.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentChatBinding
import com.example.smalltalks.model.remote_protocol.MessageDto
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ChatFragment(
    private val receiver: User
) : BaseFragment<ChatViewModel, FragmentChatBinding>() {

    override val viewModel by viewModels<ChatViewModel>()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentChatBinding =
        { inflater, container ->
            FragmentChatBinding.inflate(inflater, container, false)
        }

    private val adapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ChatFragment.adapter
        }

        lifecycleScope.launchWhenResumed {
            viewModel.data.collect {
                adapter.add(
                    MessageItem(it, false)
                )
            }
        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text.toString()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(receiver.id, message)
                adapter.add(
                    MessageItem(
                        MessageDto(viewModel.me, message), true
                    )
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_back -> {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        return true
    }
}