package com.hanbikan.nook.core.database.repository

import com.hanbikan.nook.core.database.dao.TutorialTaskDao
import com.hanbikan.nook.core.database.translator.toData
import com.hanbikan.nook.core.database.translator.toDomain
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TutorialTaskRepositoryImpl @Inject constructor(
    private val tutorialTaskDao: TutorialTaskDao
) : TutorialTaskRepository {
    override fun getTutorialTasksByUserId(userId: Int): Flow<List<TutorialTask>> {
        return tutorialTaskDao.getTasksByUserId(userId).map { tutorialTasks ->
            tutorialTasks.map { it.toDomain() }
        }
    }

    override fun getTutorialTasksByUserIdAndDay(userId: Int, day: Int): Flow<List<TutorialTask>> {
        return tutorialTaskDao.getTasksByUserIdAndDay(userId, day).map { tutorialTasks ->
            tutorialTasks.map { it.toDomain() }
        }
    }

    override suspend fun insertTutorialTasks(tutorialTaskList: List<TutorialTask>) {
        tutorialTaskDao.insertTutorialTasks(*tutorialTaskList.map { it.toData() }.toTypedArray())
    }

    override suspend fun updateTutorialTask(tutorialTask: TutorialTask) {
        tutorialTaskDao.updateTutorialTask(tutorialTask.toData())
    }

    override suspend fun resetTutorialTasks() {
        tutorialTaskDao.resetTutorialTasks()
    }
}