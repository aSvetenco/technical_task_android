package com.sa.gorestuserstask.presentation.ui.users.di

import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.domain.usecase.DeleteUserUseCase
import com.sa.gorestuserstask.domain.usecase.GetPageCountUseCase
import com.sa.gorestuserstask.domain.usecase.GetUsersFromLastPageUseCase
import dagger.Module
import dagger.Provides

@Module
class UserListModule {

    @Provides
    fun getUserFromLastPageUseCase(
        getPageCountUseCase: GetPageCountUseCase,
        repository: UserRepository
    ) = GetUsersFromLastPageUseCase(getPageCountUseCase, repository)

    @Provides
    fun getPageCountUseCase(
        repository: UserRepository
    ) = GetPageCountUseCase(repository)

    @Provides
    fun deleteUserUseCase(
        getUsersFromLastPageUseCase:GetUsersFromLastPageUseCase,
        repository: UserRepository
    ) = DeleteUserUseCase(getUsersFromLastPageUseCase, repository)
}
