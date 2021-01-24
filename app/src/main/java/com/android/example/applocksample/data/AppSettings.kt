package com.android.example.applocksample.data

interface AppSettings {

    /**
     * Returns whether the application is secured with a Biometrics, PIN, pattern or password.
     */
    var isApplicationSecure: Boolean
}

class AppSettingsImpl : AppSettings {
    override var isApplicationSecure: Boolean = true // memory cache
}
