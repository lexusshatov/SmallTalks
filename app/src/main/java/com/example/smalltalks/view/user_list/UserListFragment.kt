package com.example.smalltalks.view.user_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>() {

    override val viewModel by viewModels<UserListViewModel>()
    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    private val adapter by lazy {
        UserListAdapter(requireActivity().supportFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Users"

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserListFragment.adapter
        }

        viewModel.data.observe(viewLifecycleOwner, { list ->
            adapter.submitList(list.filter { it.name.isNotEmpty() })
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_back -> {
                requireActivity().onBackPressed()
            }
        }
        return true
    }
}