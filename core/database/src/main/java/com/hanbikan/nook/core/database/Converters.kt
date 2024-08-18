package com.hanbikan.nook.core.database

import androidx.room.TypeConverter
import com.hanbikan.nook.core.domain.model.common.Detail
import com.hanbikan.nook.core.domain.model.common.MonthToTimes
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    // List<Detail>
    @TypeConverter
    fun fromDetailList(details: List<Detail>?): String? {
        if (details.isNullOrEmpty()) {
            return null
        }
        return Json.encodeToString(details)
    }

    @TypeConverter
    fun toDetailList(string: String?): List<Detail>? {
        if (string.isNullOrEmpty()) {
            return null
        }
        return Json.decodeFromString(string)
    }


    // TimesByMonth
    @TypeConverter
    fun fromTimesByMonth(map: MonthToTimes): String {
        if (map.value.isEmpty()) {
            return ""
        }
        return Json.encodeToString(map.value.mapKeys { it.key.toString() })
    }

    @TypeConverter
    fun toTimesByMonth(string: String): MonthToTimes {
        if (string.isEmpty()) {
            return MonthToTimes(mapOf())
        }
        val decodeFromString = Json.decodeFromString<Map<Int, String>>(string)
        return MonthToTimes(decodeFromString)
    }

    // List<String>
    @TypeConverter
    fun fromStringList(details: List<String>): String {
        if (details.isEmpty()) {
            return ""
        }
        return Json.encodeToString(details)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        if (string.isEmpty()) {
            return listOf()
        }
        return Json.decodeFromString(string)
    }
}