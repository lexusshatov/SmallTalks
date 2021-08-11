package com.example.smalltalks.view.user_list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.repository.local.PreferencesData
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.view.BackPressedHandler
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.base.OnBackPressed
import com.example.smalltalks.view.chat.ChatFragment
import com.example.smalltalks.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>(),
    OnBackPressed {

    @Inject
    lateinit var preferencesData: PreferencesData

    private val backPressedHandler = BackPressedHandler(
        action = { showToast("Click again for logout") },
        exit = {
            preferencesData.deleteUserName()
            requireActivity().apply {
                finish()
                startActivity(intent)
            }
        },
        clicksToExit = 2
    )


    override val viewModel by viewModels<UserListViewModel>()
    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    private val adapter by lazy {
        UserListAdapter {
            navigateToFragment(ChatFragment.newInstance(it), true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Users"

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UserListFragment.adapter
        }

        viewModel.data.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.filter { it.name.isNotEmpty() })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_back -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onBackPressed() {
        backPressedHandler.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedHandler.destroy()
    }
}