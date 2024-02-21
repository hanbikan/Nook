package com.hanbikan.nook.feature.todo.di

import com.hanbikan.nook.core.domain.usecase.UpdateTasksIfEmptyUseCase
import com.hanbikan.nook.feature.todo.usecase.UpdateTasksIfEmptyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UpdateTasksIfEmptyUseCaseModule {
    @Binds
    fun bindsUpdateTasksIfEmptyUseCase(
        updateTasksIfEmptyUseCaseImpl: UpdateTasksIfEmptyUseCaseImpl,
    ): UpdateTasksIfEmptyUseCase
}