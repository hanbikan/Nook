package com.hanbikan.nook.core.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

inline fun <T1, T2> executeIfBothNonNull(a: T1?, b: T2?, block: (T1, T2) -> Any) {
    if (a != null && b != null) {
        block(a, b)
    }
}

fun <T1, T2> Map<out T1, T2>.forEachIndexed(action: (Int, Pair<T1, T2>) -> Unit) {
    var index = 0
    forEach { (t1, t2) ->
        action(index, Pair(t1, t2))
        index += 1
    }
}

fun getCurrentMonth(minuteOffset: Int): Int {
    val calendar = getCalendar(minuteOffset)
    return calendar.get(Calendar.MONTH) + 1
}

fun getCurrentHour(minuteOffset: Int): Int {
    val calendar = getCalendar(minuteOffset)
    return calendar.get(Calendar.HOUR_OF_DAY)
}

fun getCalendar(minuteOffset: Int): Calendar {
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.MINUTE, minuteOffset)
    return calendar
}

fun Calendar.format(language: String): String {
    val dateFormat = if (language == "ko") {
        SimpleDateFormat("yy/MM/dd HH:mm", Locale.getDefault())
    } else {
        SimpleDateFormat("MM/dd/yy HH:mm", Locale.getDefault())
    }
    return dateFormat.format(time)
}

/**
 *  두 Calendar 객체의 시간 차이를 분 단위로 반환합니다.(this - [calendar])
 */
fun Calendar.getMinutesDifference(calendar: Calendar): Int {
    // 두 시간의 차이를 밀리초 단위로 계산
    val differenceInMillis = timeInMillis - calendar.timeInMillis

    // 밀리초 차이를 분 단위로 변환
    val differenceInMinutes = differenceInMillis / (1000 * 60)

    return differenceInMinutes.toInt()
}