package com.hanbikan.nook.feature.todo.usecase

import android.content.Context
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.repository.TaskRepository
import com.hanbikan.nook.core.domain.usecase.UpdateTasksIfEmptyUseCase
import com.hanbikan.nook.feature.todo.model.createInitialTasks
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTasksIfEmptyUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    @ApplicationContext private val context: Context,
) : UpdateTasksIfEmptyUseCase {
    override suspend fun invoke(userId: Int) {
        val tasks = taskRepository.getAllTasksByUserId(userId)
        if (tasks.first().isEmpty()) {
            val tasksToInsert = Task.createInitialTasks(userId, context)
            taskRepository.insertTasks(tasksToInsert)
        }
    }
}