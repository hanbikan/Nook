package com.hanbikan.nook.core.domain.model

interface MonthlyCollectible: Collectible {
    val timesByMonth: Map<Int, String> // 1: "NA", 3: "4 PM - 9 AM", 9: "All day"
}

fun String.parseTimeRange(): List<Int> {
    if (this == "NA") {
        return listOf()
    } else if(this == "All day") {
        return (0 until 24).toList()
    }

    val hours = mutableListOf<Int>()

    // Define regex patterns to match time components
    val pattern = Regex("""(\d{1,2})\s*(AM|PM)\s*-\s*(\d{1,2})\s*(AM|PM)""")
    val matchResult = pattern.find(this)

    if (matchResult != null) {
        val (startHourStr, startPeriod, endHourStr, endPeriod) = matchResult.destructured

        val startHour = convertTo24Hour(startHourStr.toInt(), startPeriod)
        var endHour = convertTo24Hour(endHourStr.toInt(), endPeriod)

        if (endHour < startHour) {
            endHour += 24 // Crosses over to the next day
        }

        for (hour in startHour until endHour + 1) {
            hours.add(hour % 24)
        }
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
fun convertTo24Hour(hour: Int, period: String): Int {
    return when (period.uppercase()) {
        "AM" -> if (hour == 12) 0 else hour
        "PM" -> if (hour == 12) 12 else hour + 12
        else -> hour
    }
}