package com.hanbikan.nooknook.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanbikan.nooknook.core.database.dao.TaskDao
import com.hanbikan.nooknook.core.database.dao.UserDao
import com.hanbikan.nooknook.core.database.entity.TaskEntity
import com.hanbikan.nooknook.core.database.entity.UserEntity

@Database(
    version = 3,
    entities = [
        TaskEntity::class,
        UserEntity::class
    ],
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3)
    ],
    exportSchema = true
)
abstract class NnDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}