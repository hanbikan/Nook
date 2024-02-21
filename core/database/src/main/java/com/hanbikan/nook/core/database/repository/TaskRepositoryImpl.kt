package com.hanbikan.nook.core.database.repository

import com.hanbikan.nook.core.database.dao.TaskDao
import com.hanbikan.nook.core.database.translator.toData
import com.hanbikan.nook.core.database.translator.toDomain
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.repository.TaskRepository
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

    override suspend fun insertTasks(taskList: List<Task>) {
        taskDao.insertTasks(*taskList.map { it.toData() }.toTypedArray())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toData())
    }

    override suspend fun deleteTaskById(id: Int) {
        taskDao.deleteTaskById(id)
    }

    override suspend fun resetAllDailyTasks() {
        taskDao.resetAllDailyTasks()
    }
}