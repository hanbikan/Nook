package com.hanbikan.nook.feature.phone

import android.content.Context
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.TaskRepository
import com.hanbikan.nook.core.domain.repository.TutorialTaskRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.usecase.AddUserUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTasksIfEmptyUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTasksIfEmptyUseCase
import com.hanbikan.nook.feature.todo.usecase.UpdateTasksIfEmptyUseCaseImpl
import com.hanbikan.nook.feature.tutorial.usecase.UpdateTutorialTasksIfEmptyUseCaseImpl
import com.nook.core.domain_test.repository.testAppStateRepository
import com.nook.core.domain_test.repository.testTaskRepository
import com.nook.core.domain_test.repository.testTutorialTaskRepository
import com.nook.core.domain_test.repository.testUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class AddUserUseCaseTest {

    @Mock
    lateinit var context: Context

    private lateinit var userRepository: UserRepository
    private lateinit var appStateRepository: AppStateRepository
    private lateinit var taskRepository: TaskRepository
    private lateinit var tutorialTaskRepository: TutorialTaskRepository
    private lateinit var updateTasksIfEmptyUseCase: UpdateTasksIfEmptyUseCase
    private lateinit var updateTutorialTasksIfEmptyUseCase: UpdateTutorialTasksIfEmptyUseCase

    private lateinit var useCase: AddUserUseCase

    @Before
    fun setup() {
        context = mock<Context> {
            on { getString(anyInt()) }.thenReturn("")
        }

        userRepository = testUserRepository
        appStateRepository = testAppStateRepository
        taskRepository = testTaskRepository
        tutorialTaskRepository = testTutorialTaskRepository
        updateTasksIfEmptyUseCase = UpdateTasksIfEmptyUseCaseImpl(
            taskRepository,
            context
        )
        updateTutorialTasksIfEmptyUseCase = UpdateTutorialTasksIfEmptyUseCaseImpl(
            tutorialTaskRepository,
            context
        )
        useCase = AddUserUseCase(
            userRepository,
            appStateRepository,
            updateTasksIfEmptyUseCase,
            updateTutorialTasksIfEmptyUseCase
        )
    }

    @Test
    fun addUser_updateActiveUserIdAndTasksAndTutorialTasks() = runTest {
        val userId = userRepository.getAllUsers().first().maxOf { it.id } + 1
        val userToAdd = User(
            id = userId,
            name = "",
            islandName = "",
            tutorialDay = 0
        )
        useCase(userToAdd)

        // activeUserId 확인
        val currentUserId = appStateRepository.getActiveUserId().first()
        assertEquals(userId, currentUserId)
        // Task 확인
        val currentTasks = taskRepository.getAllTasksByUserId(userId).first()
        assertTrue(currentTasks.isNotEmpty())
        // TutorialTask 확인
        val currentTutorialTasks = tutorialTaskRepository.getTutorialTasksByUserId(userId).first()
        assertTrue(currentTutorialTasks.isNotEmpty())
    }
}