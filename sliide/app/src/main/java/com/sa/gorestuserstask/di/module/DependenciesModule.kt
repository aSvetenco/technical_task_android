package com.sa.gorestuserstask.di.module

import android.content.res.Resources
import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.presentation.ui.adduser.di.AddUserDependencies
import com.sa.gorestuserstask.presentation.utils.DomainErrorMapper
import com.sa.gorestuserstask.presentation.ui.users.di.UserListDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependenciesModule {

    @Singleton
    @Provides
    fun provideUserListDependencies(
        repository: UserRepository,
        resources: Resources
    ) = object : UserListDependencies {

        override fun repository(): UserRepository = repository

        override fun domainErrorMapper(): DomainErrorMapper = DomainErrorMapper(resources)
    }


    @Singleton
    @Provides
    fun provideAddUserDependencies(
        repository: UserRepository,
        resources: Resources
    ) = object : AddUserDependencies {

        override fun repository(): UserRepository = repository

        override fun domainErrorMapper(): DomainErrorMapper = DomainErrorMapper(resources)
    }
}
