package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GetTutorialDayRangeUseCase @Inject constructor(
    private val tutorialTaskRepository: TutorialTaskRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: Int?): Flow<IntRange?> {
        return if (userId == null) {
            flowOf(null)
        } else {
            tutorialTaskRepository.getTutorialTasksByUserId(userId).mapLatest { tutorialTasks ->
                var minTutorialDay: Int? = null
                var maxTutorialDay: Int? = null
                tutorialTasks.forEach {
                    minTutorialDay = min(minTutorialDay ?: Int.MAX_VALUE, it.day) // null이면 it.day 대입
                    maxTutorialDay = max(maxTutorialDay ?: 0, it.day) // null이면 it.day 대입
                }
                maxTutorialDay?.let { minTutorialDay?.rangeTo(it) }
            }
        }
    }
}