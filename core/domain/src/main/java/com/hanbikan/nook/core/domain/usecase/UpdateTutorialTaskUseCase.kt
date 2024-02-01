package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import javax.inject.Inject

class UpdateTutorialTaskUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    suspend operator fun invoke(tutorialTask: TutorialTask) {
        tutorialTaskRepository.updateTutorialTask(tutorialTask)
    }
}