package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
    private val initializeTutorialTasksUseCase: InitializeTutorialTasksUseCase,
) {
    suspend operator fun invoke(user: User) {
        val addedUserId = userRepository.insertUser(user)

        initializeTutorialTasksUseCase(addedUserId)

        // 첫 계정을 생성한 것이라면 active user로 등록
        if (appStateRepository.getActiveUserId().first() == null) {
            appStateRepository.setActiveUserId(addedUserId)
        }
    }
}