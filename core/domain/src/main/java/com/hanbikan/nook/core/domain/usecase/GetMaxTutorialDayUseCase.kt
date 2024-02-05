package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.max

class GetMaxTutorialDayUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    suspend operator fun invoke(userId: Int): Int? {
        val tutorialTasks = tutorialTaskRepository.getTutorialTasksByUserId(userId)
        var maxTutorialDay: Int? = null
        tutorialTasks.first().forEach {
            maxTutorialDay = max(maxTutorialDay ?: 0, it.day) // elvis를 넣은 이유는 null일 때는 day를 그대로 넣기 위함입니다.(0 + day)
        }
        return maxTutorialDay
    }
}