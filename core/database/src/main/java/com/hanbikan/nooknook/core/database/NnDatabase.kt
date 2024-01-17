package com.hanbikan.nooknook.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanbikan.nooknook.core.database.dao.TaskDao
import com.hanbikan.nooknook.core.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class NnDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}