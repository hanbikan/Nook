package com.hanbikan.nook.core.database

import androidx.room.TypeConverter
import com.hanbikan.nook.core.domain.model.common.Detail
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    // List<Detail>
    @TypeConverter
    fun fromDetailList(details: List<Detail>): String {
        if (details.isEmpty()) {
            return ""
        }
        return Json.encodeToString(details)
    }

    @TypeConverter
    fun toDetailList(string: String): List<Detail> {
        if (string.isEmpty()) {
            return listOf()
        }
        return Json.decodeFromString(string)
    }


    // Map<Int, String>
    @TypeConverter
    fun fromMapIntString(map: Map<Int, String>): String {
        if (map.isEmpty()) {
            return ""
        }
        return Json.encodeToString(map.mapKeys { it.key.toString() })
    }

    @TypeConverter
    fun toMapIntString(string: String): Map<Int, String> {
        if (string.isEmpty()) {
            return mapOf()
        }
        return Json.decodeFromString<Map<String, String>>(string)
            .mapKeys { it.key.toInt() }
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