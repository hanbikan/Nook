package com.hanbikan.nook.feature.tutorial.di

import com.hanbikan.nook.core.domain.usecase.InitializeTutorialTasksUseCase
import com.hanbikan.nook.feature.tutorial.usecase.InitializeTutorialTasksUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InitializeTutorialTasksUseCaseModule {
    @Binds
    fun bindsInitializeTutorialTasksUseCase(
        initializeTutorialTasksUseCaseImpl: InitializeTutorialTasksUseCaseImpl,
    ): InitializeTutorialTasksUseCase
}