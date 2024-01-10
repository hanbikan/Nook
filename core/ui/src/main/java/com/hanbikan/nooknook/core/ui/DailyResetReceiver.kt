package com.hanbikan.nooknook.core.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hanbikan.nooknook.core.domain.usecase.ResetAllTasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyResetReceiver @Inject constructor(
    private val resetAllTasksUseCase: ResetAllTasksUseCase
): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            resetAllTasksUseCase()
        }
    }
}