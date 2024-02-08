package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.TutorialTask
import kotlinx.coroutines.flow.Flow

interface TutorialTaskRepository {
    fun getTutorialTasksByUserId(userId: Int): Flow<List<TutorialTask>>

    fun getTutorialTasksByUserIdAndDay(userId: Int, day: Int): Flow<List<TutorialTask>>

    suspend fun insertTutorialTasks(tutorialTaskList: List<TutorialTask>)

    suspend fun updateTutorialTask(tutorialTask: TutorialTask)

    suspend fun resetTutorialTasks()
}