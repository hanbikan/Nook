package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}