package com.hanbikan.nook.feature.museum.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hanbikan.nook.core.domain.model.common.TimeRange
import com.hanbikan.nook.core.domain.model.common.MonthToTimes.Companion.ALL_DAY
import com.hanbikan.nook.feature.museum.R

@Composable
fun getMonthList(): List<String> {
    return listOf(
        stringResource(id = R.string.january),
        stringResource(id = R.string.february),
        stringResource(id = R.string.march),
        stringResource(id = R.string.april),
        stringResource(id = R.string.may),
        stringResource(id = R.string.june),
        stringResource(id = R.string.july),
        stringResource(id = R.string.august),
        stringResource(id = R.string.september),
        stringResource(id = R.string.october),
        stringResource(id = R.string.november),
        stringResource(id = R.string.december),
    )
}

// 예시: "2월 - 10월: 하루 종일"
@Composable
fun TimeRange.display(): String {
    val monthList = getMonthList()
    val startMonthDisplay = monthList.getOrElse(startMonth - 1) { "" }
    val endMonthDisplay = monthList.getOrElse(endMonth - 1) { "" }
    val hourRangeDisplay = if (hourRange == ALL_DAY) {
        stringResource(id = R.string.all_day)
    } else {
        hourRange
    }

    return if (startMonth == endMonth) {
        "$startMonthDisplay: $hourRangeDisplay"
    } else {
        "$startMonthDisplay - $endMonthDisplay: $hourRangeDisplay"
    }
}

// 예시: "2월 - 10월"
@Composable
fun TimeRange.displayMonth(): String {
    val monthList = getMonthList()
    val startMonthDisplay = monthList.getOrElse(startMonth - 1) { "" }
    val endMonthDisplay = monthList.getOrElse(endMonth - 1) { "" }

    return if (startMonth == endMonth) {
        "$startMonthDisplay"
    } else {
        "$startMonthDisplay - $endMonthDisplay"
    }
}

// formatTime(3, 6) returns "03:00~05:59"
fun formatTime(startHour: Int, endHour: Int): String {
    return String.format("%02d:00~%02d:59", startHour, endHour - 1)
}