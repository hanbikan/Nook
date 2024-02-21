package com.hanbikan.nook.feature.tutorial.di

import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTasksIfEmptyUseCase
import com.hanbikan.nook.feature.tutorial.usecase.UpdateTutorialTasksIfEmptyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UpdateTutorialTasksIfEmptyUseCaseModule {
    @Binds
    fun bindsUpdateTutorialTasksIfEmptyUseCase(
        updateTutorialTasksIfEmptyUseCaseImpl: UpdateTutorialTasksIfEmptyUseCaseImpl,
    ): UpdateTutorialTasksIfEmptyUseCase
}