package com.sa.gorestuserstask.data.repository

import com.sa.gorestuserstask.data.RequestExecutor
import com.sa.gorestuserstask.data.mapper.UserMapper
import com.sa.gorestuserstask.data.remote.GorestUsersApiService
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.domain.Output

class UserRepositoryImpl(
    private val service: GorestUsersApiService,
    private val executor: RequestExecutor
) : UserRepository {
    override suspend fun getPagesCount(): Output<Int> =
        executor.execute(
            request = { service.getUsers() },
            success = { response ->
                response.meta?.pagination?.pages
            })


    override suspend fun getUsers(page: Int): Output<List<User>> =
        executor.execute(
            request = { service.getUsers(page) },
            success = { response ->
                response.data.map { model ->
                    UserMapper().mapFromDtoToDomain(model)
                }
            })


    override suspend fun addUser(user: User): Output<User> =
        executor.execute(
            request = {
                service.addUser(
                    UserMapper().mapFromDomainToDto(user)
                )
            },
            success = { response ->
                UserMapper().mapFromDtoToDomain(response.data)
            })


    override suspend fun deleteUser(id: Int): Output<Unit> = executor.execute(
        request = { service.deleteUser(id) }
    )

}
