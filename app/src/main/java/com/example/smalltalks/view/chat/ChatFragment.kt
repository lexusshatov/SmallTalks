package com.example.smalltalks.view.chat

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentChatBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.chat.adapter.ChatAdapter
import com.example.smalltalks.viewmodel.ChatViewModel
import com.natife.example.domain.dto.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>() {

    private lateinit var receiver: User

    @Inject
    lateinit var viewModelFactory: ChatViewModel.Factory

    override val viewModel by viewModels<ChatViewModel> {
        ChatViewModel.provideFactory(viewModelFactory, receiver)
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentChatBinding =
        { inflater, container ->
            FragmentChatBinding.inflate(inflater, container, false)
        }

    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiver = arguments?.getSerializable(USER_KEY) as User
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = receiver.name
        adapter = ChatAdapter(viewModel.me.id)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = this@ChatFragment.adapter
        }

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editMessage.text.toString()
            if (message.isNotEmpty()) {
                binding.editMessage.setText("")
                viewModel.sendMessage(message)
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


    companion object {
        const val USER_KEY = "user"

        fun newInstance(receiver: User) = ChatFragment().apply {
            arguments = Bundle().apply {
                putSerializable(USER_KEY, receiver)
            }
        }
    }
}