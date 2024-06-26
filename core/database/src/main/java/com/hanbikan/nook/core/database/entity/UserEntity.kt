package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "island_name") val islandName: String,
    @ColumnInfo(name = "tutorial_day", defaultValue = "0") val tutorialDay: Int,
    @ColumnInfo(name = "is_north", defaultValue = "1") val isNorth: Boolean,
)