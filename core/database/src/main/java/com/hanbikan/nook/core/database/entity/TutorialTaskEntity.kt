package com.hanbikan.nook.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hanbikan.nook.core.domain.model.Detail

@Entity(tableName = "tutorial_task")
data class TutorialTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id", defaultValue = "-1") val userId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "details", defaultValue = "NULL") val details: List<Detail>?,
    @ColumnInfo(name = "day") val day: Int
)