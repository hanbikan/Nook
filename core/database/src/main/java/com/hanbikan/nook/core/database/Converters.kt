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
    fun toDetailList(detailsString: String?): List<Detail>? {
        if (detailsString == null) return null
        return Json.decodeFromString(detailsString)
    }
}