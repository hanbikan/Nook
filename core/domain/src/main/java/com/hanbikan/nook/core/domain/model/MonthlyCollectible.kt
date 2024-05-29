package com.hanbikan.nook.core.domain.model

interface MonthlyCollectible: Collectible {
    val timesByMonth: Map<Int, String> // 1: "NA", 3: "4 PM - 9 AM", 9: "All day"

    fun belongsToMonth(month: Int): Boolean {
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
    if (this == MonthlyCollectible.NOT_AVAILABLE) {
        return listOf()
    } else if(this == MonthlyCollectible.ALL_DAY) {
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