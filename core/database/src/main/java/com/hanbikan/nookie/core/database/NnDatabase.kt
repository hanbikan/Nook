package com.hanbikan.nookie.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanbikan.nookie.core.database.dao.TaskDao
import com.hanbikan.nookie.core.database.dao.UserDao
import com.hanbikan.nookie.core.database.entity.TaskEntity
import com.hanbikan.nookie.core.database.entity.UserEntity

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