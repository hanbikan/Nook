package com.hanbikan.nooknook.core.database.di

import android.content.Context
import androidx.room.Room
import com.hanbikan.nooknook.core.database.NnDatabase
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
    fun providesNnDatabase(
        @ApplicationContext context: Context
    ) : NnDatabase = Room.databaseBuilder(
        context,
        NnDatabase::class.java,
        "nn-database"
    ).build()
}