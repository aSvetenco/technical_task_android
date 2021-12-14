package com.sa.gorestuserstask.domain.repository

import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.Output

interface UserRepository {
    suspend fun getPagesCount(): Output<Int>
    suspend fun getUsers(page: Int): Output<List<User>>
    suspend fun addUser(user: User): Output<User>
    suspend fun deleteUser(id: Int): Output<Unit>
}
