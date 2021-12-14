package com.sa.gorestuserstask.data.remote.error

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sa.gorestuserstask.data.remote.ErrorResponse
import com.sa.gorestuserstask.domain.entity.Error
import okhttp3.ResponseBody
import timber.log.Timber


class ApiErrorMapper(private val gson: Gson) {

    fun toDomainError(throwable: Throwable) = when (throwable) {
        is ApiException -> Error.ApiError(
                code = throwable.httpCode,
                errors = throwable.errorBody?.let { body ->
                    parseErrorBody(body).map {
                        Error.ErrorDetails(it.field ?: "", it.message ?: "")
                    }
                } ?: listOf(),
        )
        else -> Error.OtherError(throwable)
    }

    private fun parseErrorBody(body: ResponseBody): List<ErrorResponse> =
            try {
                val type = object : TypeToken<List<ErrorResponse>>() {}.type
                gson.fromJson(body.string(), type)
            } catch (expected: Throwable) {
                Timber.e(expected)
                listOf()
            }

}
