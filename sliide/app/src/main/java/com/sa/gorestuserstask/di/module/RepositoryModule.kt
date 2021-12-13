package com.sa.gorestuserstask.di.module

import com.google.gson.Gson
import com.sa.gorestuserstask.data.RequestExecutor
import com.sa.gorestuserstask.data.remote.GorestUsersApiService
import com.sa.gorestuserstask.data.remote.error.ApiErrorMapper
import com.sa.gorestuserstask.data.repository.UserRepositoryImpl
import com.sa.gorestuserstask.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: GorestUsersApiService,
        executor: RequestExecutor
    ): UserRepository =
        UserRepositoryImpl(userService, executor)

    @Provides
    @Singleton
    fun userService(retrofit: Retrofit): GorestUsersApiService =
        retrofit.create(GorestUsersApiService::class.java)

    @Provides
    fun provideExecutor(mapper: ApiErrorMapper): RequestExecutor =
        RequestExecutor(mapper)

    @Provides
    fun provideErrorMapper(gson: Gson) = ApiErrorMapper(gson)
}
