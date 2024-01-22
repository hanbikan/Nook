package com.hanbikan.nookie.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id", defaultValue = "-1") val userId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)