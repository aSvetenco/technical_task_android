package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.Output
import com.sa.gorestuserstask.domain.entity.Error
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.repository.UserRepository

class GetUsersFromLastPageUseCase(
    private val getPageCountUseCase: GetPageCountUseCase,
    private val repository: UserRepository
) : UseCase<Unit, List<User>> {
    override suspend fun invoke(input: Unit): Output<List<User>> {
        return when (val result = getPageCountUseCase.invoke(Unit)) {
            is Output.Success -> repository.getUsers(result.data)
            is Output.Failure -> Output.Failure(Error.GeneralError)
        }
    }
}
