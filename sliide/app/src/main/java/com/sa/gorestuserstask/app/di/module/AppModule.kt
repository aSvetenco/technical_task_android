package com.sa.gorestuserstask.app.di.module

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun resources(app: Application): Resources = app.resources
}
