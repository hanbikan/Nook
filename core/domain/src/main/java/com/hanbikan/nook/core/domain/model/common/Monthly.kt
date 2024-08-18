package com.hanbikan.nook.core.domain.model.common

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
        val timesByMonth = getCurrentMonthToTimes(isNorth)
        return timesByMonth.value.containsKey(month) && timesByMonth.getTimesOrNull(month) != NOT_AVAILABLE
    }
}