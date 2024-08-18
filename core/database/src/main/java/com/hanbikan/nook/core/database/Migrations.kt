package com.hanbikan.nook.core.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.beginTransaction()
        try {
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
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}