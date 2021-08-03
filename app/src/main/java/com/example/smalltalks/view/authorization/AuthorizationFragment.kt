package com.example.smalltalks.view.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
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

        userName?.let {
            binding.buttonLogin.isClickable = false
            binding.editTextTextPersonName.setText(it)
            viewModel.connect(it)
        }

        binding.apply {
            buttonLogin.setOnClickListener {
                buttonLogin.isClickable = false
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
                    buttonLogin.isClickable = true
                }
            }
        }

        viewModel.data.observe(viewLifecycleOwner, {
            binding.buttonLogin.isClickable = true
            if (it) {
                navigateToUsers()
            } else {
                showToast("Error on connecting")
            }
        })
    }

    private fun navigateToUsers() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_container,
                UserListFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val USER_PREFERENCES = "User_preferences"
        const val USER_NAME = "User_name"
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}