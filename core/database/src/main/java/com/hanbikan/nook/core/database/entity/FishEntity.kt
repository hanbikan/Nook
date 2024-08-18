package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "fish",
    primaryKeys = ["user_id", "number"]
)
data class FishEntity(
    @ColumnInfo(name = "user_id") val userId: Int,
    val name: String,
    val number: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "times_by_month_north", defaultValue = "") val timesByMonthNorth: Map<Int, String>,
    @ColumnInfo(name = "is_collected", defaultValue = "0") val isCollected: Boolean,
    @ColumnInfo(defaultValue = "") val location: String,


    @ColumnInfo(name = "render_url", defaultValue = "") val renderUrl: String,
    @ColumnInfo(defaultValue = "") val rarity: String,
    @ColumnInfo(name = "total_catch", defaultValue = "0") val totalCatch: Int,
    @ColumnInfo(name = "sell_nook", defaultValue = "0") val sellNook: Int,
    @ColumnInfo(name = "tank_width", defaultValue = "0.0") val tankWidth: Float,
    @ColumnInfo(name = "tank_length", defaultValue = "0.0") val tankLength: Float,
    @ColumnInfo(defaultValue = "") val catchphrases: List<String>,

    @ColumnInfo(name = "is_north", defaultValue = "1") val isNorth: Boolean,
    @ColumnInfo(name = "times_by_month_south", defaultValue = "") val timesByMonthSouth: Map<Int, String>,

    @ColumnInfo(name = "shadow_size", defaultValue = "") val shadowSize: String,
)