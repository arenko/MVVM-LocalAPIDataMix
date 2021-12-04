package com.arenko

import android.app.Application
import android.content.ContentProvider
import android.content.Context
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RijksApp : Application() , Configuration.Provider{

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}