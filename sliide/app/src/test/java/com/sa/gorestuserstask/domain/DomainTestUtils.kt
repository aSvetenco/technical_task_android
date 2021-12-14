package com.sa.gorestuserstask.domain

import com.sa.gorestuserstask.domain.entity.Error
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.repository.UserRepository


val user1 = User(name = "User1", email = "user1@user.com")
val user2 = User(name = "User2", email = "user2@user.com")

val users = listOf(user1, user2)

val NOT_FOUND =
    Error.ApiError(404, listOf(Error.ErrorDetails(message = "The requested resource does not exist.")))
val DATA_VALIDATION_FAILD =
    Error.ApiError(422, listOf(Error.ErrorDetails(message = "Data validation failed")))

/**
 * MockRepository by default return happy path.
 * In order to change pass lambda with desired behavior
 */

fun mockRepository(
    getPagesCount: () -> Output<Int> = { Output.Success(2) },
    getUsers: (Int) -> Output<List<User>> = { Output.Success(users) },
    addUser: (User) -> Output<User> = { Output.Success(User()) },
    deleteUser: (Int) -> Output<Unit> = { Output.Success(Unit) }
): UserRepository = object : UserRepository {
    override suspend fun getPagesCount(): Output<Int> =
        getPagesCount()

    override suspend fun getUsers(page: Int): Output<List<User>> =
        getUsers(page)

    override suspend fun addUser(user: User): Output<User> =
        addUser(user)

    override suspend fun deleteUser(id: Int): Output<Unit> =
        deleteUser(id)
}
