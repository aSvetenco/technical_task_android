package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.Output

interface UseCase<I, O> {
    suspend fun invoke(input: I): Output<O>
}
