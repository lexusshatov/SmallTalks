package com.example.smalltalks.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.smalltalks.R

abstract class BaseFragment<VM, VB : ViewBinding> : Fragment() {

    abstract val viewModel: VM
    private var toast: Toast? = null

    abstract val viewBindingProvider: (LayoutInflater, ViewGroup?) -> VB
    private var bindingInternal: VB? = null
    val binding: VB
        get() = bindingInternal!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingInternal = viewBindingProvider(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInternal = null
    }

    fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun navigateToFragment(fragment: Fragment, backstack: Boolean) {
        val fragmentTransaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_container,
                fragment
            )
        fragmentTransaction.apply {
            if (backstack) addToBackStack(null)
            commit()
        }
    }
}