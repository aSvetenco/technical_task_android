package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.*
import com.sa.gorestuserstask.domain.entity.Error
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetUsersFromLastPageUseCaseTest {

    @Test
    fun `useCase returns list of users when getPageCountUseCase and getUsers not fails`() {

        val repository = mockRepository()

        val getPageCountUseCase = GetPageCountUseCase(repository)
        val getUsersFromLastPageUseCase = GetUsersFromLastPageUseCase(
            getPageCountUseCase, repository
        )
        runBlocking {
            val result = getUsersFromLastPageUseCase.invoke(Unit)
            val data = (result as Output.Success).data

            assertEquals(user1, data[0])
            assertEquals(user2, data[1])
        }
    }

    @Test
    fun `useCase returns error when getPageCountUseCase fails`() {

        val repository = mockRepository(
            getPagesCount = { Output.Failure(NOT_FOUND) }
        )

        val getPageCountUseCase = GetPageCountUseCase(repository)
        val getUsersFromLastPageUseCase = GetUsersFromLastPageUseCase(
            getPageCountUseCase, repository
        )
        runBlocking {
            val result = getUsersFromLastPageUseCase.invoke(Unit)
            val error = (result as Output.Failure).error
            val message = (error as Error.ApiError).errors[0].message

            assertEquals(NOT_FOUND.errors[0].message, message)
        }
    }

    @Test
    fun `useCase returns error when repository getUsers() fails`() {

        val repository = mockRepository(
            getUsers = { Output.Failure(NOT_FOUND) }
        )

        val getPageCountUseCase = GetPageCountUseCase(repository)
        val getUsersFromLastPageUseCase = GetUsersFromLastPageUseCase(
            getPageCountUseCase, repository
        )
        runBlocking {
            val result = getUsersFromLastPageUseCase.invoke(Unit)
            val error = (result as Output.Failure).error
            val message = (error as Error.ApiError).errors[0].message

            assertEquals(NOT_FOUND.errors[0].message, message)
        }
    }

}



