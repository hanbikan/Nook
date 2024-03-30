package com.hanbikan.nook.core.database

import androidx.room.TypeConverter
import com.hanbikan.nook.core.domain.model.Detail
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromDetailList(details: List<Detail>?): String? {
        if (details == null) return null
        return Json.encodeToString(details)
    }

    @TypeConverter
    fun toDetailList(string: String?): List<Detail>? {
        if (string == null) return null
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromMapIntString(map: Map<Int, String>): String {
        return Json.encodeToString(map.mapKeys { it.key.toString() })
    }

    @TypeConverter
    fun toMapIntString(string: String): Map<Int, String> {
        return Json.decodeFromString<Map<String, String>>(string)
            .mapKeys { it.key.toInt() }
    }
}