package com.sa.gorestuserstask.presentation.ui.users.di

import com.sa.gorestuserstask.di.AppComponent
import com.sa.gorestuserstask.di.module.ViewModelFactoryModule
import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.presentation.ui.users.UserListFragment
import com.sa.gorestuserstask.presentation.utils.DomainErrorMapper
import dagger.Component

@Component(
    dependencies = [UserListDependencies::class],
    modules = [
        UserListVMModule::class,
        UserListModule::class,
        ViewModelFactoryModule::class
    ]
)
interface UserListComponent {

    fun inject(fragment: UserListFragment)

    companion object {
        fun buildComponent() = DaggerUserListComponent.builder()
            .userListDependencies(AppComponent.get().provideUserListDependencies())
            .build()
    }
}

interface UserListDependencies {
    fun repository(): UserRepository
    fun domainErrorMapper(): DomainErrorMapper
}

