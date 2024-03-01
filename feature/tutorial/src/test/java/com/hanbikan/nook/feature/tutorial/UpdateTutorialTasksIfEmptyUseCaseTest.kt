package com.hanbikan.nook.feature.tutorial

import android.content.Context
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTasksIfEmptyUseCase
import com.hanbikan.nook.feature.tutorial.usecase.UpdateTutorialTasksIfEmptyUseCaseImpl
import com.nook.core.domain_test.data.tutorialTasksTestData
import com.nook.core.domain_test.data.userTestData
import com.nook.core.domain_test.repository.TestTutorialTaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock


@RunWith(MockitoJUnitRunner::class)
class UpdateTutorialTasksIfEmptyUseCaseTest {
    private lateinit var testTutorialTaskRepository: TutorialTaskRepository
    private lateinit var useCase: UpdateTutorialTasksIfEmptyUseCase

    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        context = mock<Context> {}

        testTutorialTaskRepository = TestTutorialTaskRepository()
        useCase = UpdateTutorialTasksIfEmptyUseCaseImpl(
            testTutorialTaskRepository,
            context
        )
    }

    @Test
    fun updateTutorialTasks_ifNotEmpty_keepsPreviousTasksUnchanged() = runTest {
        val userIdWithTutorialTask = tutorialTasksTestData[0].userId
        val prevTutorialTasks = testTutorialTaskRepository.getTutorialTasksByUserId(userIdWithTutorialTask).first()
        useCase(userIdWithTutorialTask)
        val tutorialTasks = testTutorialTaskRepository.getTutorialTasksByUserId(userIdWithTutorialTask).first()
        assertEquals(prevTutorialTasks, tutorialTasks)
    }

    @Test
    fun updateTutorialTasks_ifEmpty_updatesTutorialTasks() = runTest {
        val userIdWithoutTutorialTask = userTestData.find { user ->
            // tutorialTasksTestData에서 자신의 id를 찾을 수 없는 경우 찾기
            tutorialTasksTestData.firstOrNull { it.userId == user.id } == null
        }!!.id
        val prevTutorialTasks = testTutorialTaskRepository.getTutorialTasksByUserId(userIdWithoutTutorialTask).first()
        useCase(userIdWithoutTutorialTask)
        val tutorialTask = testTutorialTaskRepository.getTutorialTasksByUserId(userIdWithoutTutorialTask)
        assertNotEquals(prevTutorialTasks, tutorialTask)
    }
}