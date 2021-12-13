package com.sa.gorestuserstask.presentation.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.databinding.FragmentUserListBinding
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.presentation.ui.users.di.UserListComponent
import com.sa.gorestuserstask.presentation.utils.ViewModelFactory
import javax.inject.Inject

class UserListFragment : Fragment(), UserListAdapter.Listener {

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding
        get() = requireNotNull(_binding) {
            "The binding was either accessed too late or too early. " +
                    "Check ${this.javaClass.name} and it's presentation logic!"
        }

    @Inject
    lateinit var factory: ViewModelFactory

    private val adapter = UserListAdapter(this)
    private val userListVM by viewModels<UserListViewModel> { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UserListComponent.buildComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListVM.apply {
            fetchUsers()
            isLoading.observe(viewLifecycleOwner, ::isLoading)
            onUserListLoaded.observe(viewLifecycleOwner, adapter::submitList)
            onErrorLE.observe(viewLifecycleOwner, ::showToast)
            onSuccessLE.observe(viewLifecycleOwner, ::showToast)
        }
        binding.userList.adapter = adapter
        binding.refresh.setOnRefreshListener { userListVM.fetchUsers() }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.refresh.isRefreshing = isLoading
    }

    private fun showToast(@StringRes messageRes: Int) {
        showToast(getString(messageRes))
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: User) {
    }

    override fun onItemLongClick(item: User) {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_user_message, item.name))
            .setPositiveButton(android.R.string.ok) { d, _ ->
                userListVM.deleteUser(item.id)
                d.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { d, _ -> d.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding.userList.adapter = null
        _binding = null
    }
}
