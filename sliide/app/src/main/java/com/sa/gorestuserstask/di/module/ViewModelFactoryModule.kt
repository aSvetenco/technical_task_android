package com.sa.gorestuserstask.di.module

import androidx.lifecycle.ViewModelProvider
import com.sa.gorestuserstask.presentation.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
