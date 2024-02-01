package com.hanbikan.nook.core.database.di

import com.hanbikan.nook.core.database.repository.TaskRepositoryImpl
import com.hanbikan.nook.core.database.repository.TutorialTaskRepositoryImpl
import com.hanbikan.nook.core.database.repository.UserRepositoryImpl
import com.hanbikan.nook.core.domain.repository.TaskRepository
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseRepositoryModule {

    @Binds
    fun bindsTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl,
    ): TaskRepository

    @Binds
    fun bindsTutorialTaskRepository(
        tutorialTaskRepositoryImpl: TutorialTaskRepositoryImpl,
    ): TutorialTaskRepository

    @Binds
    fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository
}