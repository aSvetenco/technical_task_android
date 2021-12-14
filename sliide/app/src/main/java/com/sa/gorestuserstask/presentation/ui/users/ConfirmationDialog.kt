package com.sa.gorestuserstask.presentation.ui.users

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.presentation.di.UserListComponent
import com.sa.gorestuserstask.presentation.utils.ViewModelFactory
import javax.inject.Inject

class ConfirmationDialog : DialogFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val userListVM by activityViewModels<UserListViewModel> { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UserListComponent.buildComponent().inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val name = requireArguments().getString(USER_NAME)
        val userId = requireArguments().getInt(USER_ID)
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_user_confirmation_message, name))
            .setPositiveButton(R.string.delete) { d, _ ->
                userListVM.deleteUser(userId)
                d.dismiss()
            }
            .setNegativeButton(R.string.cancel) { d, _ -> d.dismiss() }
            .show()
    }

    companion object {
        private const val USER_NAME = "USER_NAME"
        private const val USER_ID = "USER_ID"
        private const val TAG_CONFIRMATION_DIALOG = "TAG_CONFIRMATION_DIALOG"

        fun showConfirmationDialog(
            name: String,
            userId: Int,
            fragmentManager: FragmentManager
        ) {
            ConfirmationDialog().apply {
                arguments = bundleOf(USER_NAME to name, USER_ID to userId)
                show(fragmentManager, TAG_CONFIRMATION_DIALOG)
            }
        }
    }
}
