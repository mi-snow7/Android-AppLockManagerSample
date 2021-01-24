package com.android.example.applocksample.module

import com.android.example.applocksample.MainActivity
import com.android.example.applocksample.lockmanager.AppLockActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun appLockActivity(): AppLockActivity

}