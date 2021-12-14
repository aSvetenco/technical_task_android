package com.sa.gorestuserstask.presentation.ui.adduser.di

import androidx.lifecycle.ViewModel
import com.sa.gorestuserstask.di.module.ViewModelKey
import com.sa.gorestuserstask.presentation.ui.adduser.AddUserViewModel
import com.sa.gorestuserstask.presentation.ui.users.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddUserVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddUserViewModel::class)
    fun bindUAddUserViewModel(addUserViewModel: AddUserViewModel): ViewModel
}
