package com.sa.gorestuserstask.presentation.di

import com.sa.gorestuserstask.app.di.AppComponent
import com.sa.gorestuserstask.app.di.module.ViewModelFactoryModule
import com.sa.gorestuserstask.domain.repository.UserRepository
import com.sa.gorestuserstask.presentation.ui.adduser.AddUserDialog
import com.sa.gorestuserstask.presentation.ui.users.ConfirmationDialog
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

    fun inject(dialog: ConfirmationDialog)

    fun inject(dialog: AddUserDialog)

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

