package com.hanbikan.nook.core.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hanbikan.nook.core.domain.usecase.ResetAllDailyTasksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DailyResetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var resetAllDailyTasksUseCase: ResetAllDailyTasksUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            resetAllDailyTasksUseCase()
        }
    }
}