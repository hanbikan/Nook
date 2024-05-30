package com.hanbikan.nook.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hanbikan.nook.core.database.dao.CollectionDao
import com.hanbikan.nook.core.database.dao.TaskDao
import com.hanbikan.nook.core.database.dao.TutorialTaskDao
import com.hanbikan.nook.core.database.dao.UserDao
import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.database.entity.TaskEntity
import com.hanbikan.nook.core.database.entity.TutorialTaskEntity
import com.hanbikan.nook.core.database.entity.UserEntity

@Database(
    version = 13,
    entities = [
        TaskEntity::class,
        UserEntity::class,
        TutorialTaskEntity::class,
        FishEntity::class,
        BugEntity::class,
    ],
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3),
        AutoMigration (from = 3, to = 4),
        AutoMigration (from = 4, to = 5),
        AutoMigration (from = 5, to = 6),
        AutoMigration (from = 7, to = 8),
        AutoMigration (from = 8, to = 9),
        AutoMigration (from = 9, to = 10),
        AutoMigration (from = 10, to = 11),
        AutoMigration (from = 11, to = 12),
        AutoMigration (from = 12, to = 13),
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NkDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun tutorialTaskDao(): TutorialTaskDao
    abstract fun userDao(): UserDao
    abstract fun collectionDao(): CollectionDao
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 기존 테이블 삭제 후 생성(reset)
        db.execSQL("DROP TABLE tutorial_task")
        db.execSQL("""
            CREATE TABLE tutorial_task (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                user_id INTEGER NOT NULL DEFAULT -1,
                name TEXT NOT NULL,
                is_done INTEGER NOT NULL,
                details TEXT DEFAULT NULL,
                day INTEGER NOT NULL
            )
        """)
    }
}