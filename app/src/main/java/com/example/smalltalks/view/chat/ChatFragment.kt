package com.example.smalltalks.view.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentChatBinding
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.user_list.UserListFragment
import com.example.smalltalks.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter

@AndroidEntryPoint
class ChatFragment private constructor(
    private val input: BufferedReader,
    private val output: PrintWriter,
    private val user: User
): BaseFragment<ChatViewModel, FragmentChatBinding>() {

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

        viewModel.start(input, output, user)
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                viewModel.data.collect {
                    withContext(Dispatchers.Main) {
                        adapter.add(it)
                    }
                }
            }
        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text.toString()
            if (message.isNotEmpty()) {
                viewModel.sendMessage(message)
            }
        }
    }

    companion object {

        fun newInstance(input: BufferedReader, output: PrintWriter, user: User) =
            ChatFragment(input, output, user)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_list -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, UserListFragment.newInstance(input, output, user))
                    .addToBackStack(null)
                    .commit()
            }
        }
        return true
    }
}