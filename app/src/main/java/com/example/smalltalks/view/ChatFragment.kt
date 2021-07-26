package com.example.smalltalks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.databinding.FragmentChatBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.AuthorizationViewModel
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

        fun newInstance() = ChatViewModel()
    }
}