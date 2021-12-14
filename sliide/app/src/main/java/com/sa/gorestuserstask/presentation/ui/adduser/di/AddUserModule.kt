package com.sa.gorestuserstask.presentation.ui.adduser.di

import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.domain.usecase.AddUserUseCase
import com.sa.gorestuserstask.domain.usecase.DeleteUserUseCase
import com.sa.gorestuserstask.domain.usecase.GetPageCountUseCase
import com.sa.gorestuserstask.domain.usecase.GetUsersFromLastPageUseCase
import com.sa.gorestuserstask.presentation.ui.adduser.UserDataValidator
import dagger.Module
import dagger.Provides

@Module
class AddUserModule {

    @Provides
    fun addUserUseCase(
        repository: UserRepository
    ) = AddUserUseCase(repository)

    @Provides
    fun provideUserDataValidator() = UserDataValidator()
}
