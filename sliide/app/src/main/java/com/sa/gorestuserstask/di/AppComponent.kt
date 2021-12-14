package com.sa.gorestuserstask.di

import android.app.Application
import com.sa.gorestuserstask.di.module.*
import com.sa.gorestuserstask.presentation.ui.adduser.di.AddUserDependencies
import com.sa.gorestuserstask.presentation.ui.users.di.UserListDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DependenciesModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun provideUserListDependencies(): UserListDependencies
    fun provideAddUserDependencies(): AddUserDependencies

    companion object {
        @Volatile
        private var instance: AppComponent? = null

        fun get(): AppComponent = requireNotNull(
            instance
        ) { "AppComponent is not initialized yet. Call init first." }

        fun init(component: AppComponent) {
            require(instance == null) { "${AppComponent::class.simpleName} is already initialized." }
            instance = component
        }
    }
}
