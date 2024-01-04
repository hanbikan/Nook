package com.hanbikan.nooknook.core.database.di

import com.hanbikan.nooknook.core.database.NnDatabase
import com.hanbikan.nooknook.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesTaskDao(
        database: NnDatabase
    ) : TaskDao = database.taskDao()
}