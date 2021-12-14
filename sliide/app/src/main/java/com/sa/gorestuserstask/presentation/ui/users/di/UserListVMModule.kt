package com.sa.gorestuserstask.presentation.ui.users.di

import androidx.lifecycle.ViewModel
import com.sa.gorestuserstask.di.module.ViewModelKey
import com.sa.gorestuserstask.presentation.ui.users.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserListVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    fun bindUserListViewModel(userListViewModel: UserListViewModel): ViewModel
}
