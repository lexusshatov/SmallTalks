package com.example.smalltalks.view.user_list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.view.BackPressedHandler
import com.example.smalltalks.view.authorization.AuthorizationFragment
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.base.OnBackPressed
import com.example.smalltalks.view.chat.ChatFragment
import com.example.smalltalks.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>(),
    OnBackPressed {
    private val backPressedHandler = BackPressedHandler(
        actions = hashMapOf(
            1 to { showToast("Click again for logout") },
            2 to {
                requireActivity().apply {
                    getSharedPreferences(
                        AuthorizationFragment.USER_PREFERENCES,
                        Context.MODE_PRIVATE
                    )
                        .edit {
                            remove(AuthorizationFragment.USER_NAME)
                            apply()
                        }
                    finish()
                    startActivity(intent)
                }
            }
        )
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