package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksByUserId(userId: Int): Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTaskById(id: Int)

    suspend fun resetAllDailyTasks()
}