package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.model.Task
import com.hanbikan.nookie.core.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }
}