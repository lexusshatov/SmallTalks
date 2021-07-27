package com.example.smalltalks.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.example.smalltalks.R
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu_opened, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_list -> {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        return true
    }

    companion object {

        fun newInstance() = UserListFragment()
    }
}