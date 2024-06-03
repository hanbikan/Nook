package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "sea_creature",
    primaryKeys = ["user_id", "number"]
)
data class SeaCreatureEntity(
    @ColumnInfo(name = "user_id") val userId: Int,
    val name: String,
    val number: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "times_by_month") val timesByMonth: Map<Int, String>,
    val isCollected: Boolean,
)