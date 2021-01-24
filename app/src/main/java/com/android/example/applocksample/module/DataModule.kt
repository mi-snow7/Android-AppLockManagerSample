package com.android.example.applocksample.module

import com.android.example.applocksample.data.AppSettings
import com.android.example.applocksample.data.AppSettingsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DataModule {

    @Provides
    @Singleton
    fun provideAppSettings(): AppSettings = AppSettingsImpl()

}
