package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksByUserIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(userId: Int): Flow<List<Task>> {
        return taskRepository.getAllTasksByUserId(userId)
    }
}