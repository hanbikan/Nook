package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: Int) {
        taskRepository.deleteTaskById(id)
    }
}