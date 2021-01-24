package com.android.example.applocksample.module

import com.android.example.applocksample.data.AppSettings
import com.android.example.applocksample.lockmanager.AppLockManager
import com.android.example.applocksample.lockmanager.AppLockManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ApplicationModule {

    @Provides
    @Singleton
    fun provideAppLockManager(appSettings: AppSettings): AppLockManager =
        AppLockManagerImpl(appSettings)
}