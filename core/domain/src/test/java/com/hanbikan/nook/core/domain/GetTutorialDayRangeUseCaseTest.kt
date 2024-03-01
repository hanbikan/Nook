package com.hanbikan.nook.core.domain

import com.hanbikan.nook.core.domain.usecase.GetTutorialDayRangeUseCase
import com.nook.core.domain_test.repository.testTutorialTaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTutorialDayRangeUseCaseTest {

    private val useCase = GetTutorialDayRangeUseCase(
        tutorialTaskRepository = testTutorialTaskRepository
    )

    @Test
    fun getTutorialDayRange_returnsMinMaxDayRangeForUser() = runTest {
        val testUserId = 0
        val result = useCase(testUserId).first()

        val tutorialTasks = testTutorialTaskRepository.getTutorialTasksByUserId(testUserId).first()
            .filter { it.userId == testUserId }
        val minDay = tutorialTasks.minOf { it.day }
        val maxDay = tutorialTasks.maxOf { it.day }

        assertEquals(result, minDay.rangeTo(maxDay))
    }
}