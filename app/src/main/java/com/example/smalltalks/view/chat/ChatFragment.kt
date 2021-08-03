package com.example.smalltalks.view.chat

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

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
        (requireActivity() as AppCompatActivity).supportActionBar?.title = receiver.name

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ChatFragment.adapter
        }

        // FIXME: 03.08.2021  
        viewModel
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                viewModel.data.collect {
                    withContext(Dispatchers.Main) {
                        adapter.add(it)
                    }
                    if (!it.fromMe) viewModel.saveMessage(it)
                }
            }
        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text.toString()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(receiver.id, message)
                val messageItem = MessageItem(
                    MessageDto(viewModel.me, message), true
                )
                adapter.add(messageItem)
                viewModel.saveMessage(messageItem)
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