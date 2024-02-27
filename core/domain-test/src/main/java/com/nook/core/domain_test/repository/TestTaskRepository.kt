package com.nook.core.domain_test.repository

import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.repository.TaskRepository
import com.nook.core.domain_test.data.tasksTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val testTaskRepository = TestTaskRepository()

class TestTaskRepository : TaskRepository {
    override fun getAllTasksByUserId(userId: Int): Flow<List<Task>> {
        val tasks = tasksTestData.filter { it.userId == userId }
        return flowOf(tasks)
    }

    override suspend fun insertTasks(taskList: List<Task>) {
        tasksTestData = tasksTestData + taskList
    }

    override suspend fun updateTask(task: Task) {
        val indexToUpdate = tasksTestData.indexOfFirst { it.id == task.id }
        if (indexToUpdate == -1) return

        val newTasks = tasksTestData.toMutableList().apply {
            this[indexToUpdate] = task
        }
        tasksTestData = newTasks
    }

    override suspend fun deleteTaskById(id: Int) {
        val newTasks = tasksTestData.filter { it.id != id }
        tasksTestData = newTasks
    }

    override suspend fun resetAllDailyTasks() {
        tasksTestData = listOf()
    }
}