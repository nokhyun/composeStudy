package com.nokhyun.timer

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics

class TimerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}