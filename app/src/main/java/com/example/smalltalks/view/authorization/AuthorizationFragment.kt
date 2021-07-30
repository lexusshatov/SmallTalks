package com.example.smalltalks.view.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.chat.ChatFragment
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
        userName?.let { viewModel.connect(it) }

        binding.buttonLogin.setOnClickListener {
            val userName = binding.editTextTextPersonName.text.toString()
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

        viewModel.data.observe(viewLifecycleOwner, {
            if (it) {
                navigateToChat()
            } else {
                showToast("Error on connecting")
            }
        })
    }

    private fun navigateToChat() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_container,
                ChatFragment.newInstance(
                    viewModel.input,
                    viewModel.output,
                    viewModel.user
                )
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"

        fun newInstance() = AuthorizationFragment()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}