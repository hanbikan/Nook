package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class FishEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val number: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "times_by_month") val timesByMonth: Map<Int, String>,
    val isCollected: Boolean,
)