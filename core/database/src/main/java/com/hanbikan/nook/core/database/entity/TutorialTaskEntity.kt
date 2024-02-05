package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tutorial_task")
data class TutorialTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id", defaultValue = "-1") val userId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "detail_description", defaultValue = "NULL") val detailDescription: String?,
    @ColumnInfo(name = "detail_image_id", defaultValue = "NULL") val detailImageId: Int?,
    @ColumnInfo(name = "day") val day: Int
)