package com.example.smalltalks.view.user_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.model.remote_protocol.User
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter

@AndroidEntryPoint
class UserListFragment private constructor(
    private val input: BufferedReader,
    private val output: PrintWriter,
    private val user: User
): BaseFragment<UserListViewModel, FragmentUserListBinding>() {

    override val viewModel by viewModels<UserListViewModel>()
    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    private val adapter = UserListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserListFragment.adapter
        }

        viewModel.start(input, output, user)
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                viewModel.data.collect {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(it)
                    }
                }
            }
        }
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

        fun newInstance(input: BufferedReader, output: PrintWriter, user: User) =
            UserListFragment(input, output, user)
    }
}