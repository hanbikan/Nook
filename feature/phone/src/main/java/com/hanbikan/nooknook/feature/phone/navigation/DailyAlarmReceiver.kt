package com.hanbikan.nooknook.feature.phone.navigation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hanbikan.nooknook.core.domain.usecase.ResetAllTasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// 매일 일정 시각에 호출됩니다. 기본적으로 새벽 5시에 호출되며, 타임슬립 유저를 위한 시각 변경을 고려해야 합니다.
class DailyAlarmReceiver @Inject constructor(
    private val resetAllTasksUseCase: ResetAllTasksUseCase
): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            resetAllTasksUseCase()
        }
    }
}