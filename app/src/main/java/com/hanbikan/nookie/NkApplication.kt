package com.hanbikan.nookie

import android.app.Application
import com.hanbikan.nookie.core.ui.receiver.DailyResetScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DailyResetScheduler.scheduleDailyAlarm(this)
    }
}