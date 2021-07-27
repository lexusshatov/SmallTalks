package com.example.smalltalks.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.chat.ChatFragment
import com.example.smalltalks.viewmodel.AuthorizationViewModel

class AuthorizationFragment : BaseFragment<AuthorizationViewModel, FragmentAuthorizationBinding>() {

    override val viewModel by viewModels<AuthorizationViewModel>{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AuthorizationViewModel.Factory.create(userId) as T
            }
        }
    }

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentAuthorizationBinding =
        { inflater, container ->
            FragmentAuthorizationBinding.inflate(inflater, container, false)
        }

    private val userId by lazy {
        requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .getString(USER_ID, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, ChatFragment.newInstance())
                .commit()
        }
    }

    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_ID = "User_ID"
        const val USER_NAME = "User_name"


        fun newInstance() = AuthorizationFragment()
    }
}