package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import javax.inject.Inject

class InitializeTutorialTasksUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    suspend operator fun invoke() {
        val tutorialTasks: List<TutorialTask> = listOf(
            // TODO
        )
        tutorialTaskRepository.insertTutorialTasks(tutorialTasks)
    }
}