package com.example.smalltalks.view.user_list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smalltalks.R
import com.example.smalltalks.databinding.FragmentUserListBinding
import com.example.smalltalks.view.authorization.AuthorizationFragment
import com.example.smalltalks.view.base.BaseFragment
import com.example.smalltalks.view.base.IOnBackPressed
import com.example.smalltalks.view.chat.ChatFragment
import com.example.smalltalks.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment<UserListViewModel, FragmentUserListBinding>(),
    IOnBackPressed {
    private var clickCount = 0

    override val viewModel by viewModels<UserListViewModel>()
    override val viewBindingProvider: (LayoutInflater, ViewGroup?) -> FragmentUserListBinding =
        { inflater, container ->
            FragmentUserListBinding.inflate(inflater, container, false)
        }

    private val adapter by lazy {
        UserListAdapter {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, ChatFragment.newInstance(it))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Users"

        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(TIMEOUT_EXIT)
                if (clickCount > 0) clickCount--
            }
        }

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
        clickCount++
        when (clickCount) {
            1 -> showToast("Click again for log out")
            2 -> {
                activity?.apply {
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
        }
    }

    companion object {
        const val TIMEOUT_EXIT = 1500L
    }
}