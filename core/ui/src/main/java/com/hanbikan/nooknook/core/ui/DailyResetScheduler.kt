package com.hanbikan.nooknook.core.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

/**
 *  * 매일 오전 5시에 [DailyResetReceiver]를 호출합니다.
 */
class DailyResetScheduler {

    companion object {
        fun scheduleDailyAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyResetReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                PendingIntentRequestCode.DAILY_RESET_RECEIVER,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // TODO: 타임슬립 유저를 위한 데일리 리셋 시간 변경
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 5)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)

                // 현재 시간이 이미 데일리 리셋 시간을 지났다면 다음 날로 설정
                if (after(Calendar.getInstance())) {
                    add(Calendar.DATE, 1)
                }
            }

            alarmManager.setRepeating(
                AlarmManager.RTC,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }
}