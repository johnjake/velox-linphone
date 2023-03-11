package com.velox.org

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@Suppress("SpellCheckingInspection")
@HiltAndroidApp
class VeloxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
