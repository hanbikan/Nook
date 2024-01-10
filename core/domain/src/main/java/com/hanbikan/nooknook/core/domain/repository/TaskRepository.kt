package com.hanbikan.nooknook.core.domain.repository

import com.hanbikan.nooknook.core.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTaskById(id: Int)

    suspend fun resetAllTasks()
}