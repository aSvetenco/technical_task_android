package com.sa.gorestuserstask.presentation.di

import androidx.lifecycle.ViewModel
import com.sa.gorestuserstask.app.di.module.ViewModelKey
import com.sa.gorestuserstask.presentation.ui.adduser.AddUserViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(AddUserViewModel::class)
    fun bindUAddUserViewModel(addUserViewModel: AddUserViewModel): ViewModel
}
