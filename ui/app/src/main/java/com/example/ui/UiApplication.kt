package com.example.ui

import android.app.Application
import timber.log.Timber

class UiApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}