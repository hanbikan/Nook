package com.hanbikan.nook.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hanbikan.nook.core.database.dao.CollectionDao
import com.hanbikan.nook.core.database.dao.TaskDao
import com.hanbikan.nook.core.database.dao.TutorialTaskDao
import com.hanbikan.nook.core.database.dao.UserDao
import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.database.entity.SeaCreatureEntity
import com.hanbikan.nook.core.database.entity.TaskEntity
import com.hanbikan.nook.core.database.entity.TutorialTaskEntity
import com.hanbikan.nook.core.database.entity.UserEntity

@Database(
    version = 20,
    entities = [
        TaskEntity::class,
        UserEntity::class,
        TutorialTaskEntity::class,
        FishEntity::class,
        BugEntity::class,
        SeaCreatureEntity::class,
    ],
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3),
        AutoMigration (from = 3, to = 4),
        AutoMigration (from = 4, to = 5),
        AutoMigration (from = 5, to = 6),
        // MIGRATION_6_7
        AutoMigration (from = 7, to = 8),
        AutoMigration (from = 8, to = 9),
        AutoMigration (from = 9, to = 10),
        AutoMigration (from = 10, to = 11),
        AutoMigration (from = 11, to = 12),
        AutoMigration (from = 12, to = 13),
        AutoMigration (from = 13, to = 14),
        AutoMigration (from = 14, to = 15, spec = NkDatabase.AutoMigration_14_15::class),
        AutoMigration (from = 15, to = 16),
        AutoMigration(from = 16, to = 17, spec = NkDatabase.AutoMigration_16_17::class),
        AutoMigration(from = 17, to = 18, spec = NkDatabase.AutoMigration_17_18::class),
        AutoMigration (from = 18, to = 19),
        AutoMigration (from = 19, to = 20),
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)

abstract class NkDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun tutorialTaskDao(): TutorialTaskDao
    abstract fun userDao(): UserDao
    abstract fun collectionDao(): CollectionDao

    @DeleteColumn(tableName = "sea_creature", columnName = "location")
    class AutoMigration_14_15 : AutoMigrationSpec

    @DeleteColumn(tableName = "bug", columnName = "id")
    @DeleteColumn(tableName = "fish", columnName = "id")
    @DeleteColumn(tableName = "sea_creature", columnName = "id")
    class AutoMigration_16_17 : AutoMigrationSpec

    @RenameColumn.Entries(
        RenameColumn(tableName = "fish", fromColumnName = "times_by_month", toColumnName = "times_by_month_north"),
        RenameColumn(tableName = "fish", fromColumnName = "isCollected", toColumnName = "is_collected"),
        RenameColumn(tableName = "bug", fromColumnName = "times_by_month", toColumnName = "times_by_month_north"),
        RenameColumn(tableName = "bug", fromColumnName = "isCollected", toColumnName = "is_collected"),
        RenameColumn(tableName = "sea_creature", fromColumnName = "times_by_month", toColumnName = "times_by_month_north"),
        RenameColumn(tableName = "sea_creature", fromColumnName = "isCollected", toColumnName = "is_collected")
    )
    class AutoMigration_17_18 : AutoMigrationSpec
}