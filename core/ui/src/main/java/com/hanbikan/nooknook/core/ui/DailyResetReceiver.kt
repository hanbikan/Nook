package com.hanbikan.nooknook.core.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hanbikan.nooknook.core.domain.usecase.ResetAllTasksUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DailyResetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var resetAllTasksUseCase: ResetAllTasksUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            resetAllTasksUseCase()
        }
    }
}