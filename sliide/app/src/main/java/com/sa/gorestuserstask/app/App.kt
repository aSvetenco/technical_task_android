package com.sa.gorestuserstask.app

import android.app.Application
import com.sa.gorestuserstask.app.di.AppComponent
import com.sa.gorestuserstask.app.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponent.init(
            DaggerAppComponent.builder().application(this).build()
        )
    }
}
