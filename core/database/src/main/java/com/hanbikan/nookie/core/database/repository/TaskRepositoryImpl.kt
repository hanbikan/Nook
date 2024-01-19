package com.hanbikan.nookie.core.database.repository

import com.hanbikan.nookie.core.database.dao.TaskDao
import com.hanbikan.nookie.core.database.translator.toData
import com.hanbikan.nookie.core.database.translator.toDomain
import com.hanbikan.nookie.core.domain.model.Task
import com.hanbikan.nookie.core.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAllTasksByUserId(userId: Int): Flow<List<Task>> {
        return taskDao.getAllTasksByUserId(userId).map { tasks ->
            tasks.map { it.toDomain() }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTasks(task.toData())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toData())
    }

    override suspend fun deleteTaskById(id: Int) {
        taskDao.deleteTaskById(id)
    }

    override suspend fun resetAllTasks() {
        taskDao.resetAllTasks()
    }
}