package com.hanbikan.nook.feature.tutorial.usecase

import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.usecase.InitializeTutorialTasksUseCase
import com.hanbikan.nook.feature.tutorial.model.createInitialTutorialTasks
import javax.inject.Inject

class InitializeTutorialTasksUseCaseImpl @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository,
) : InitializeTutorialTasksUseCase {
    override suspend operator fun invoke(userId: Int) {
        val tutorialTasks = TutorialTask.createInitialTutorialTasks(userId)
        tutorialTaskRepository.insertTutorialTasks(tutorialTasks)
    }
}