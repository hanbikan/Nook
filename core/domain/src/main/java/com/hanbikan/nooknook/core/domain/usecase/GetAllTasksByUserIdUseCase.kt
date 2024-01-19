package com.hanbikan.nooknook.core.domain.usecase

import com.hanbikan.nooknook.core.domain.model.Task
import com.hanbikan.nooknook.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAllTasksByUserIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(userId: Int?): Flow<List<Task>> {
        if (userId == null) return flowOf(listOf())
        return taskRepository.getAllTasksByUserId(userId)
    }
}