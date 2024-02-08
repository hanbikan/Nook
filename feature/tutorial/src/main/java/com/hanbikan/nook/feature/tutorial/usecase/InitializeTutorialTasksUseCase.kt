package com.hanbikan.nook.feature.tutorial.usecase

import android.content.Context
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.usecase.InitializeTutorialTasksUseCase
import com.hanbikan.nook.feature.tutorial.model.createInitialTutorialTasks
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InitializeTutorialTasksUseCaseImpl @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository,
    @ApplicationContext private val context: Context
) : InitializeTutorialTasksUseCase {
    override suspend operator fun invoke(userId: Int) {
        val tutorialTasks = TutorialTask.createInitialTutorialTasks(userId, context)
        tutorialTaskRepository.insertTutorialTasks(tutorialTasks)
    }
}