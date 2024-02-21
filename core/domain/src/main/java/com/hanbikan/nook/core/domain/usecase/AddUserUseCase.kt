package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
    private val updateTasksIfEmptyUseCase: UpdateTasksIfEmptyUseCase,
    private val updateTutorialTasksIfEmptyUseCase: UpdateTutorialTasksIfEmptyUseCase,
) {
    suspend operator fun invoke(user: User) {
        val addedUserId = userRepository.insertUser(user)

        // 후속 작업
        appStateRepository.setActiveUserId(addedUserId)
        updateTasksIfEmptyUseCase(addedUserId)
        updateTutorialTasksIfEmptyUseCase(addedUserId)
    }
}