package com.sa.gorestuserstask.presentation.ui.adduser.di

import com.sa.gorestuserstask.di.AppComponent
import com.sa.gorestuserstask.di.module.ViewModelFactoryModule
import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.presentation.ui.adduser.AddUserDialog
import com.sa.gorestuserstask.presentation.ui.users.UserListFragment
import com.sa.gorestuserstask.presentation.ui.users.di.DaggerUserListComponent
import com.sa.gorestuserstask.presentation.ui.users.di.UserListDependencies
import com.sa.gorestuserstask.presentation.ui.users.di.UserListModule
import com.sa.gorestuserstask.presentation.ui.users.di.UserListVMModule
import com.sa.gorestuserstask.presentation.utils.DomainErrorMapper
import dagger.Component

@Component(
    dependencies = [AddUserDependencies::class],
    modules = [
        AddUserVMModule::class,
        AddUserModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AddUserComponent {

    fun inject(fragment: AddUserDialog)

    companion object {
        fun buildComponent() = DaggerAddUserComponent.builder()
            .addUserDependencies(AppComponent.get().provideAddUserDependencies())
            .build()
    }
}

interface AddUserDependencies {
    fun repository(): UserRepository
    fun domainErrorMapper(): DomainErrorMapper
}

