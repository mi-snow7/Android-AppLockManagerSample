package com.android.example.applocksample.application

import com.android.example.applocksample.lockmanager.AppLockManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApplication : DaggerApplication() {

    @Inject
    lateinit var appLockManager: AppLockManager

    override fun onCreate() {
        super.onCreate()

        // Initialize app lock manager in onCreate
        appLockManager.init(this)
    }

    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent.factory().create(this)

}
