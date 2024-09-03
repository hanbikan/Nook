package com.hanbikan.nook.core.domain.model.common

import com.hanbikan.nook.core.common.getCurrentHour
import com.hanbikan.nook.core.common.getCurrentMonth
import com.hanbikan.nook.core.domain.model.common.MonthToTimes.Companion.NOT_AVAILABLE

/**
 * An interface representing monthly collectible items.
 */
interface Monthly {
    val monthToTimesNorth: MonthToTimes
    val monthToTimesSouth: MonthToTimes

    fun getCurrentMonthToTimes(isNorth: Boolean): MonthToTimes {
        return if (isNorth) monthToTimesNorth else monthToTimesSouth
    }

    fun belongsToMonth(month: Int, isNorth: Boolean): Boolean {
        val monthToTimes = getCurrentMonthToTimes(isNorth)
        return monthToTimes.value.containsKey(month) && monthToTimes.getTimesOrNull(month) != NOT_AVAILABLE
    }

    fun isCurrentlyCollectible(isNorth: Boolean, minuteOffset: Int): Boolean {
        val currentMonth = getCurrentMonth(minuteOffset)
        val currentHour = getCurrentHour(minuteOffset)
        val monthToTimes = getCurrentMonthToTimes(isNorth)
        val times = monthToTimes.getTimesOrNull(currentMonth) ?: return false
        return currentHour in times.parseTimeRange()
    }
}