package com.hanbikan.nook.core.testing.repository

import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.testing.data.tutorialTasksTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestTutorialTaskRepository : TutorialTaskRepository {
    override fun getTutorialTasksByUserId(userId: Int): Flow<List<TutorialTask>> {
        return flowOf(tutorialTasksTestData)
    }

    override fun getTutorialTasksByUserIdAndDay(userId: Int, day: Int): Flow<List<TutorialTask>> {
        val tutorialTasks = tutorialTasksTestData.filter {
            it.userId == userId && it.day == day
        }
        return flowOf(tutorialTasks)
    }

    override suspend fun insertTutorialTasks(tutorialTaskList: List<TutorialTask>) {
        tutorialTasksTestData = tutorialTasksTestData + tutorialTaskList
    }

    override suspend fun updateTutorialTask(tutorialTask: TutorialTask) {
        val indexToUpdate = tutorialTasksTestData.indexOfFirst { it.id == tutorialTask.id }
        if (indexToUpdate == -1) return

        val newTutorialTasks = tutorialTasksTestData.toMutableList().apply {
            this[indexToUpdate] = tutorialTask
        }
        tutorialTasksTestData = newTutorialTasks.toList()
    }

    override suspend fun resetTutorialTasks() {
        tutorialTasksTestData = listOf()
    }
}