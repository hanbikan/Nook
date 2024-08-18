package com.hanbikan.nook.feature.museum.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.domain.model.common.Monthly
import com.hanbikan.nook.core.domain.model.common.TimeRange
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

@Composable
fun TimeRange.display(): String {
    val monthList = getMonthList()
    val startMonthDisplay = monthList.getOrElse(startMonth - 1) { "" }
    val endMonthDisplay = monthList.getOrElse(endMonth - 1) { "" }
    val hourRangeDisplay = if (hourRange == Monthly.ALL_DAY) {
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