package com.hanbikan.nooknook.core.database.di

import com.hanbikan.nooknook.core.database.repository.TaskRepositoryImpl
import com.hanbikan.nooknook.core.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}