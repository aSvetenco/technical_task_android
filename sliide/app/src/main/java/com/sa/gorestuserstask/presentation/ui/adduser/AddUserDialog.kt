package com.sa.gorestuserstask.presentation.ui.adduser

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.databinding.FragmentAddUserBinding
import com.sa.gorestuserstask.domain.entity.Gender
import com.sa.gorestuserstask.domain.entity.Status
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.presentation.di.UserListComponent
import com.sa.gorestuserstask.presentation.ui.users.UserListViewModel
import com.sa.gorestuserstask.presentation.utils.ViewModelFactory
import com.sa.gorestuserstask.presentation.utils.hideKeyboard
import javax.inject.Inject

class AddUserDialog : DialogFragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding: FragmentAddUserBinding
        get() = requireNotNull(_binding) {
            "The binding was either accessed too late or too early. " +
                    "Check ${this.javaClass.name} and it's presentation logic!"
        }

    @Inject
    lateinit var factory: ViewModelFactory

    private val addUserVM by viewModels<AddUserViewModel> { factory }
    private val userListVM by activityViewModels<UserListViewModel> { factory }
    private val handler = Handler(Looper.getMainLooper())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UserListComponent.buildComponent().inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addUserVM.apply {
            invalidUserEmail.observe(viewLifecycleOwner, ::setInvalidEmailError)
            invalidUserName.observe(viewLifecycleOwner, ::setInvalidNameError)
            isLoading.observe(viewLifecycleOwner, ::isLoading)
            onErrorLE.observe(viewLifecycleOwner, ::onFailedAddUser)
            onSuccessLE.observe(viewLifecycleOwner) { onSucceedUserAdded() }
        }
        setViewListeners()
    }

    private fun setViewListeners() {
        binding.run {
            addUserName.doOnTextChanged { _, _, _, _ -> addUserName.error = null }
            addUserEmail.doOnTextChanged { _, _, _, _ -> addUserEmail.error = null }
            male.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) female.isChecked = false
            }
            female.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) male.isChecked = false
            }
            active.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) inactive.isChecked = false
            }
            inactive.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) active.isChecked = false
            }
            close.setOnClickListener { dismiss() }
            addUser.setOnClickListener {
                onFailedAddUser(null)
                it.hideKeyboard()
                addUserVM.addUser(
                        User(
                                name = addUserName.text.toString(),
                                email = addUserEmail.text.toString(),
                                gender = if (male.isChecked) Gender.Male else Gender.Female,
                                status = if (active.isChecked) Status.Active else Status.Inactive
                        )
                )
            }
        }
    }

    private fun setInvalidEmailError(@StringRes errorRes: Int) {
        binding.addUserEmail.error = getString(errorRes)
    }

    private fun setInvalidNameError(@StringRes errorRes: Int) {
        binding.addUserName.error = getString(errorRes)
    }

    private fun isLoading(isLoading: Boolean) {
        binding.run {
            progressCircular.isVisible = isLoading
            addUser.text = ""
        }
    }

    private fun onFailedAddUser(errorMessage: String?) {
        binding.run {
            error.isVisible = errorMessage != null
            error.text = errorMessage
            addUser.text = getString(R.string.add_user_save)
        }
    }

    private fun onSucceedUserAdded() {
        binding.iconSuccess.isVisible = true
        userListVM.fetchUsers()
        handler.postDelayed(SUCCESS_DELAY) {
            dismiss()
        }
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SUCCESS_DELAY = 200L
    }
}
