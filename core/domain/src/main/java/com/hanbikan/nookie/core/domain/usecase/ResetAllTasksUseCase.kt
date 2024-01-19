package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.repository.TaskRepository
import javax.inject.Inject

class ResetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke() {
        taskRepository.resetAllTasks()
    }
}