package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTutorialTasksUseCase @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val initializeTutorialTasksUseCase: InitializeTutorialTasksUseCase,
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    suspend operator fun invoke() {
        tutorialTaskRepository.resetTutorialTasks()
        val users = getAllUsersUseCase().first()
        users.forEach {
            initializeTutorialTasksUseCase(it.id)
        }
    }
}