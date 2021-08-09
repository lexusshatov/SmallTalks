package com.example.smalltalks.view.authorization

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.model.repository.remote.ConnectState
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.user_list.UserListFragment
import com.example.smalltalks.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment :
    BaseFragment<AuthorizationViewModel, FragmentAuthorizationBinding>() {

    override val viewModel by viewModels<AuthorizationViewModel>()
    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentAuthorizationBinding =
        { inflater, container ->
            FragmentAuthorizationBinding.inflate(inflater, container, false)
        }

    private val userName by lazy {
        requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
            .getString(USER_NAME, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Login"
        userName?.let { binding.editTextTextPersonName.setText(it) }

        binding.apply {
            buttonLogin.setOnClickListener {
                val userName = editTextTextPersonName.text.toString()
                if (userName.isNotEmpty()) {
                    requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
                        .edit {
                            putString(USER_NAME, userName)
                            apply()
                        }
                    viewModel.connect(userName)
                } else {
                    showToast("Username must be not empty")
                }
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { state ->
            Log.d(TAG, "Connect state: ${state.javaClass.simpleName}")
            when (state) {
                is ConnectState.Nothing -> {
                    userName?.let {
                        viewModel.connect(it)
                    }
                }
                is ConnectState.Connect -> {
                    binding.buttonLogin.isClickable = false
                }
                is ConnectState.Success -> {
                    navigateToFragment(UserListFragment(), true)
                    binding.buttonLogin.isClickable = true
                }
                is ConnectState.Error -> {
                    binding.buttonLogin.isClickable = true
                    showToast(state.message)
                }
            }
        }
    }


    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"

        private val TAG = AuthorizationFragment::class.java.simpleName
    }
}