package com.hanbikan.nook.feature.tutorial.usecase

import android.content.Context
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTasksIfEmptyUseCase
import com.hanbikan.nook.feature.tutorial.model.createInitialTutorialTasks
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTutorialTasksIfEmptyUseCaseImpl @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository,
    @ApplicationContext private val context: Context,
) : UpdateTutorialTasksIfEmptyUseCase {
    override suspend operator fun invoke(userId: Int) {
        val tutorialTasks = tutorialTaskRepository.getTutorialTasksByUserId(userId)
        if (tutorialTasks.first().isEmpty()) {
            val tutorialTasksToInsert = TutorialTask.createInitialTutorialTasks(userId, context)
            tutorialTaskRepository.insertTutorialTasks(tutorialTasksToInsert)
        }
    }
}