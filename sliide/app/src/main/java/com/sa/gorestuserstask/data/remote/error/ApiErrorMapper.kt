package com.sa.gorestuserstask.data.remote.error

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.sa.gorestuserstask.domain.entity.Error
import okhttp3.ResponseBody


class ApiErrorMapper(private val gson: Gson) {

    fun toDomainError(throwable: Throwable) = when (throwable) {
        is ApiException -> Error.ApiError(
            code = throwable.httpCode,
            errors = throwable.errorBody?.let { body ->
                parseErrorBody(body)
            } ?: listOf(),
        )
        else -> Error.OtherError(throwable)
    }

    private fun parseErrorBody(body: ResponseBody): List<Error.ErrorDetails> =
        try {
            val type = object : TypeToken<List<Error.ErrorDetails>>() {}.type
            gson.fromJson(body.string(), type)
        } catch (expected: JsonParseException) {
            listOf()
        }

}
