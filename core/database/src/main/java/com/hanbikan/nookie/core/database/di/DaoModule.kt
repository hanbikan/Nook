package com.hanbikan.nookie.core.database.di

import com.hanbikan.nookie.core.database.NnDatabase
import com.hanbikan.nookie.core.database.dao.TaskDao
import com.hanbikan.nookie.core.database.dao.UserDao
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
    ): TaskDao = database.taskDao()

    @Provides
    fun providesUserDao(
        database: NnDatabase
    ): UserDao = database.userDao()
}