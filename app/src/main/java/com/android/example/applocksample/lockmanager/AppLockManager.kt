package com.android.example.applocksample.lockmanager

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.android.example.applocksample.MainActivity
import com.android.example.applocksample.data.AppSettings
import java.lang.ref.WeakReference
import javax.inject.Inject

interface AppLockManager {

    val isAppForeground: Boolean

    fun init(application: Application)
}

class AppLockManagerImpl @Inject constructor(
    private val appSettings: AppSettings,
) : AppLockManager, LifecycleObserver, Application.ActivityLifecycleCallbacks {

    // List of activities to ignore.
    private val ignoreActivities = listOf(
        MainActivity::class.java,
        AppLockActivity::class.java
    )

    private var isInitialized = false

    private var lastActivity: WeakReference<Activity>? = null

    @Volatile
    override var isAppForeground = false
        private set

    @Synchronized
    override fun init(application: Application) {
        if (isInitialized) {
            return
        }

        isInitialized = true
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        application.registerActivityLifecycleCallbacks(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onApplicationForeground() {
        isAppForeground = true

        lastActivity?.get()?.let {
            showLockActivityIfPossible(it)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onApplicationBackground() {
        isAppForeground = false
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        lastActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    private fun showLockActivityIfPossible(activity: Activity) {
        if (!shouldShowAppLockActivity(activity)) {
            return
        }

        // Show app lock screen
        activity.startActivity(Intent(activity, AppLockActivity::class.java))
    }

    /**
     * Determine whether to show the app lock activity on the given activity.
     */
    private fun shouldShowAppLockActivity(activity: Activity) =
        appSettings.isApplicationSecure
                && !ignoreActivities.contains(activity::class.java)

}
