package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.model.User
import com.hanbikan.nookie.core.domain.repository.AppStateRepository
import com.hanbikan.nookie.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.insertUser(user)

        // 첫 계정을 생성한 것이라면 active user로 등록
        if (appStateRepository.getActiveUserId().first() == null) {
            appStateRepository.setActiveUserId(user.id)
        }
    }
}