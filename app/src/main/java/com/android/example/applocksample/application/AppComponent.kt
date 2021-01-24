package com.android.example.applocksample.application

import com.android.example.applocksample.module.ActivityModule
import com.android.example.applocksample.module.ApplicationModule
import com.android.example.applocksample.module.DataModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DataModule::class,
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<MyApplication>
}