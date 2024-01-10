package com.hanbikan.nooknook

import android.app.Application
import com.hanbikan.nooknook.core.ui.DailyResetScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NnApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DailyResetScheduler.scheduleDailyAlarm(baseContext)
    }
}