package com.hanbikan.nook.core.database.di

import android.content.Context
import androidx.room.Room
import com.hanbikan.nook.core.database.MIGRATION_6_7
import com.hanbikan.nook.core.database.NkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesNkDatabase(
        @ApplicationContext context: Context
    ) : NkDatabase = Room.databaseBuilder(
        context,
        NkDatabase::class.java,
        "nk-database"
    )
        .addMigrations(MIGRATION_6_7)
        .build()
}