package com.example.smalltalks.view.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.chat.ChatFragment
import com.example.smalltalks.viewmodel.AuthorizationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationFragment :
    BaseFragment<AuthorizationViewModel, FragmentAuthorizationBinding>(),
    ContractAuthorizationView {

    override val viewModel by viewModels<AuthorizationViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AuthorizationViewModel.Factory.create(
                    userId,
                    this@AuthorizationFragment
                ) as T
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
        if (userId != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                login(
                    requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                        .getString(USER_NAME, null)!!
                )
            }
        }
        binding.buttonLogin.setOnClickListener {
            val userName = binding.editTextTextPersonName.text.toString()
            if (userName.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    login(userName)
                }
                requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                    .edit {
                        putString(
                            USER_NAME,
                            userName
                        )
                        apply()
                    }
            } else {
                showToast("Username must be not empty")
            }
        }
    }

    private suspend fun login(userName: String) {
        viewModel.connect(userName)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, ChatFragment.newInstance())
            .commit()
    }

    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_ID = "User_ID"
        const val USER_NAME = "User_name"


        fun newInstance() = AuthorizationFragment()
    }

    override fun saveUserId(userId: String) {
        requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .edit {
                putString(
                    USER_ID,
                    userId
                )
                apply()
            }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}