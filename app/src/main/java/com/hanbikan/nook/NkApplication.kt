package com.hanbikan.nook

import android.app.Application
import com.hanbikan.nook.core.ui.receiver.DailyResetScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DailyResetScheduler.scheduleDailyAlarm(this)
    }
}