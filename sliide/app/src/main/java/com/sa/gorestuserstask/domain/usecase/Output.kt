package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.entity.Error

sealed interface Output<T> {
    class Success<T>(val data: T) : Output<T>
    class Failure<T>(val error: Error) : Output<T>
}
