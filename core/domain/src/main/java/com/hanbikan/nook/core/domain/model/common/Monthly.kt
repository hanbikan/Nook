package com.hanbikan.nook.core.domain.model.common

import com.hanbikan.nook.core.domain.model.common.MonthToTimes.Companion.NOT_AVAILABLE

/**
 * An interface representing monthly collectible items.
 */
interface Monthly {
    val isNorth: Boolean
    val monthToTimesNorth: MonthToTimes
    val monthToTimesSouth: MonthToTimes

    fun getCurrentTimesByMonth(): MonthToTimes {
        return if (isNorth) monthToTimesNorth else monthToTimesSouth
    }

    fun belongsToMonth(month: Int): Boolean {
        val timesByMonth = getCurrentTimesByMonth()
        return timesByMonth.value.containsKey(month) && timesByMonth.getTimesOrNull(month) != NOT_AVAILABLE
    }
}