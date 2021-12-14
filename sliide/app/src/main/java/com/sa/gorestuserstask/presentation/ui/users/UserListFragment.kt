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
import androidx.fragment.app.activityViewModels
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.databinding.FragmentUserListBinding
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.presentation.ui.adduser.AddUserDialog
import com.sa.gorestuserstask.presentation.di.UserListComponent
import com.sa.gorestuserstask.presentation.utils.ViewModelFactory
import java.nio.file.Files.delete
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
    private val userListVM by activityViewModels<UserListViewModel> { factory }

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
            isLoading.observe(viewLifecycleOwner, ::isLoading)
            onUserListLoaded.observe(viewLifecycleOwner, adapter::submitList)
            onErrorLE.observe(viewLifecycleOwner, ::showToast)
            onSuccessLE.observe(viewLifecycleOwner, ::showToast)
        }
        binding.userList.adapter = adapter
        binding.refresh.setOnRefreshListener { userListVM.fetchUsers() }
        binding.addUserButton.setOnClickListener {
            AddUserDialog().show(parentFragmentManager, TAG_ADD_USER_DIALOG)
        }
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

    override fun onItemLongClick(item: User) {
        ConfirmationDialog.showConfirmationDialog(
            item.name,
            item.id,
            parentFragmentManager
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_ADD_USER_DIALOG = "TAG_ADD_USER_DIALOG"
    }
}
