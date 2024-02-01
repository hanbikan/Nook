package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTutorialTasksByUserIdAndDayUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    operator fun invoke(userId: Int, day: Int): Flow<List<TutorialTask>> {
        return tutorialTaskRepository.getTutorialTasksByUserIdAndDay(userId, day)
    }
}