package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GetTutorialDayRangeUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    suspend operator fun invoke(userId: Int): IntRange? {
        val tutorialTasks = tutorialTaskRepository.getTutorialTasksByUserId(userId)
        var minTutorialDay: Int? = null
        var maxTutorialDay: Int? = null
        tutorialTasks.first().forEach {
            minTutorialDay = min(minTutorialDay ?: Int.MAX_VALUE, it.day) // null이면 it.day 대입
            maxTutorialDay = max(maxTutorialDay ?: 0, it.day) // null이면 it.day 대입
        }
        return maxTutorialDay?.let { minTutorialDay?.rangeTo(it) }
    }
}