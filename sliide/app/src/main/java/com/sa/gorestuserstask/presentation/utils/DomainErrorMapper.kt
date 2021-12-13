package com.sa.gorestuserstask.presentation.utils

import android.content.res.Resources
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.domain.entity.Error

class DomainErrorMapper(private val resources: Resources) {

    fun toUiErrorMessage(error: Error): String =
        when (error) {
            is Error.ApiError -> {
                val message = error.errors.joinToString(separator = "\n") {
                    "${it.field} - ${it.message}"
                }
                if (message.isEmpty()) genericError() else message
            }
            is Error.OtherError, Error.GeneralError -> genericError()
        }

    private fun genericError() = resources.getString(R.string.generic_error_message)
}
