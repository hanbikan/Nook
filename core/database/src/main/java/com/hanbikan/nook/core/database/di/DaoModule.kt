package com.hanbikan.nook.core.database.di

import com.hanbikan.nook.core.database.NkDatabase
import com.hanbikan.nook.core.database.dao.CollectionDao
import com.hanbikan.nook.core.database.dao.TaskDao
import com.hanbikan.nook.core.database.dao.TutorialTaskDao
import com.hanbikan.nook.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesTaskDao(
        database: NkDatabase
    ): TaskDao = database.taskDao()

    @Provides
    fun providesTutorialTaskDao(
        database: NkDatabase
    ): TutorialTaskDao = database.tutorialTaskDao()

    @Provides
    fun providesUserDao(
        database: NkDatabase
    ): UserDao = database.userDao()

    @Provides
    fun providesCollectionDao(
        database: NkDatabase
    ): CollectionDao = database.collectionDao()
}