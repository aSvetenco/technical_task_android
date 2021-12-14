package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.Output
import com.sa.gorestuserstask.domain.repository.UserRepository

class GetPageCountUseCase(private val repository: UserRepository): UseCase<Unit, Int> {
    override suspend fun invoke(input: Unit): Output<Int> =
        repository.getPagesCount()
}
