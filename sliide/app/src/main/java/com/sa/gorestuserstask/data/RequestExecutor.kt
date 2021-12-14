package com.sa.gorestuserstask.data

import com.sa.gorestuserstask.data.remote.error.ApiErrorMapper
import com.sa.gorestuserstask.data.remote.error.ApiException
import com.sa.gorestuserstask.domain.entity.Error
import com.sa.gorestuserstask.domain.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RequestExecutor(
    private val errorMapper: ApiErrorMapper
) {
    suspend fun <T, R> execute(
        request: suspend () -> Response<T>,
        success: (T) -> R?
    ): Output<R> = withContext(Dispatchers.IO) {
        try {
            val response: Response<T> = request()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                success(body)?.let {
                    Output.Success(it)
                } ?: Output.Failure(Error.GeneralError)
            } else throw ApiException(response.errorBody(), response.code())

        } catch (expected: Throwable) {
            Output.Failure(errorMapper.toDomainError(expected))
        }
    }

    suspend fun execute(
        request: suspend () -> Response<Unit>,
    ): Output<Unit> = withContext(Dispatchers.IO) {
        try {
            val response: Response<Unit> = request()

            if (response.isSuccessful) Output.Success(Unit)
            else throw ApiException(response.errorBody(), response.code())

        } catch (expected: Throwable) {
            Output.Failure(errorMapper.toDomainError(expected))
        }
    }
}
