package com.hanbikan.nook.core.domain.model.common

import com.hanbikan.nook.core.domain.model.common.Monthly.Companion.ALL_DAY
import com.hanbikan.nook.core.domain.model.common.Monthly.Companion.NOT_AVAILABLE

/**
 * An interface representing monthly collectible items.
 */
interface Monthly {
    val isNorth: Boolean
    val timesByMonthNorth: Map<Int, String> // 1: "NA", 3: "4 PM - 9 AM", 9: "All day"
    val timesByMonthSouth: Map<Int, String>

    fun getCurrentTimesByMonth(): Map<Int, String> {
        return if (isNorth) timesByMonthNorth else timesByMonthSouth
    }

    fun belongsToMonth(month: Int): Boolean {
        val timesByMonth = getCurrentTimesByMonth()
        return timesByMonth.containsKey(month) && timesByMonth[month] != NOT_AVAILABLE
    }

    companion object {
        const val NOT_AVAILABLE = "NA"
        const val ALL_DAY = "All day"
    }
}

/**
 * "4 AM - 9 AM" returns [4,5,6,7,8,9]
 */
fun String.parseTimeRange(): List<Int> {
    if (this == NOT_AVAILABLE) {
        return listOf()
    } else if (this == ALL_DAY) {
        return (0 until 24).toList()
    }

    val hours = mutableListOf<Int>()

    val startHourIndex: Int = this.indexOfFirst { it.isDigit() }
    val startHour: Int = this.slice(startHourIndex..startHourIndex + 1).trim().toInt()
    val startPeriodIndex: Int = this.indexOfFirst { it == 'M' } - 1
    val startPeriod: String = this.slice(startPeriodIndex..startPeriodIndex + 1)
    val convertedStartHour = convertTo24Hour(startHour, startPeriod)

    val endHourIndex: Int = this.indexOfLast { it.isDigit() } - 1
    val endHour: Int = this.slice(endHourIndex..endHourIndex + 1).trim().toInt()
    val endPeriodIndex: Int = this.indexOfLast { it == 'M' } - 1
    val endPeriod: String = this.slice(endPeriodIndex..startPeriodIndex + 1)
    val convertedEndHour = convertTo24Hour(endHour, endPeriod)

    var currentHour = convertedStartHour
    while (currentHour != convertedEndHour) {
        hours.add(currentHour)
        currentHour = (currentHour + 1) % 24
    }

    return hours.toList()
}

/**
 * Converts a 12-hour clock time to a 24-hour clock time.
 *
 * @param hour Hour in 12-hour clock format
 * @param period "AM" or "PM"
 * @return Hour in 24-hour clock format
 */
private fun convertTo24Hour(hour: Int, period: String): Int {
    return when (period.uppercase()) {
        "AM" -> if (hour == 12) 0 else hour
        "PM" -> if (hour == 12) 12 else hour + 12
        else -> hour
    }
}

/**
 * {1="NA", 2="NA", 3="4 AM - 7 AM", 4="4 AM - 7 AM", 5="4 AM - 7 AM", 6="4 AM - 7 AM", 7="4 AM - 7 AM", 8="NA", 9="NA", 10="All day", 11="All day", 12="All day"}
 * ->
 * [(3, 7, "4 AM - 7 AM"), (10, 12, "All day")]
 */
fun Map<Int, String>.convertToTimeRanges(): List<TimeRange> {
    val result = mutableListOf<TimeRange>()
    val addedMonths = mutableSetOf<Int>()
    for (month in 1..12) {
        if (month in addedMonths) {
            continue
        }
        val hourRange = this[month]
        if (!existsHourRange(hourRange)) {
            continue
        }

        // month + 1 ~ 12을 체크하고 hourRange가 같으면 묶기
        val startMonth = month
        var endMonth = month
        addedMonths.add(month)
        for (currentMonth in month + 1..12) {
            val currentHourRange = this[currentMonth]
            if (hourRange != currentHourRange) {
                break
            }
            endMonth = currentMonth
            addedMonths.add(currentMonth)
        }

        result.add(TimeRange(startMonth, endMonth, hourRange!!))
    }

    return result
}

// Return false if null or empty or "NA"
private fun existsHourRange(hourRange: String?): Boolean {
    return !(hourRange.isNullOrEmpty() || hourRange == NOT_AVAILABLE)
}