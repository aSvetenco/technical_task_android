package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.repository.UserRepository

class DeleteUserUseCase(
    private val getUsersFromLastPageUseCase: GetUsersFromLastPageUseCase,
    private val repository: UserRepository
) : UseCase<Int, List<User>> {
    override suspend fun invoke(input: Int): Output<List<User>> =
        when (val result = repository.deleteUser(input)) {
            is Output.Success -> getUsersFromLastPageUseCase.invoke(Unit)
            is Output.Failure -> Output.Failure(result.error)
        }
}

