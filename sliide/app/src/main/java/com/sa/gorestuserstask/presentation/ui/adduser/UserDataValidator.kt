package com.sa.gorestuserstask.presentation.ui.adduser

import androidx.annotation.StringRes
import com.sa.gorestuserstask.R
import java.util.regex.Pattern

class UserDataValidator {

    var listener: UserValidationListener? = null

    fun isValid(data: ValidationData): Boolean =
        validateName(data.name) and
                validateIsEmptyEmail(data.email)

    private fun validateName(name: String): Boolean {
        val isValid = name.isNotBlank()
        if (!isValid) listener?.onInvalidUserName(R.string.validation_error_invalid_empty_field)
        return isValid
    }

    private fun validateIsEmptyEmail(email: String): Boolean {
        val isValid = email.isNotBlank() && EMAIL_ADDRESS.matcher(email).matches()
        if (!isValid) listener?.onInvalidUserEmail(R.string.validation_error_invalid_email_field)
        return isValid
    }

    interface UserValidationListener {
        fun onInvalidUserName(@StringRes errorResId: Int)
        fun onInvalidUserEmail(@StringRes errorResId: Int)
    }

    data class ValidationData(val name: String, val email: String)

    private companion object {
        val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }
}
