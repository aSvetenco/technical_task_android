package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.Output
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.repository.UserRepository

class AddUserUseCase(private val repository: UserRepository) : UseCase<User, User> {
    override suspend fun invoke(input: User): Output<User> =
        repository.addUser(input)
}
