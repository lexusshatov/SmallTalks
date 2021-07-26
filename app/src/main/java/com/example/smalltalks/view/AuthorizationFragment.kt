package com.example.smalltalks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentAuthorizationBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.AuthorizationViewModel

class AuthorizationFragment : BaseFragment<AuthorizationViewModel, FragmentAuthorizationBinding>() {

    override val viewModel by viewModels<AuthorizationViewModel>()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentAuthorizationBinding =
        { inflater, container ->
            FragmentAuthorizationBinding.inflate(inflater, container, false)
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

        fun newInstance() = AuthorizationFragment()
    }
}