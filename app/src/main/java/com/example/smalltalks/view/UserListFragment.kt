package com.example.smalltalks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.UserListViewModel

class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>() {

    override val viewModel by viewModels<UserListViewModel>()

    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO
    }

    companion object {

        fun newInstance() = AuthorizationFragment()
    }
}