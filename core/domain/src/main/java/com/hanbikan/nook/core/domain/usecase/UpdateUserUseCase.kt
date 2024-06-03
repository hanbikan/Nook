package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * 언어, 반구, 앱 버전 등이 변경될 때 호출하여 사용자에 의존하는 데이터를 갱신합니다.
 */
class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val updateFishesUseCase: UpdateFishesUseCase,
    private val updateBugsUseCase: UpdateBugsUseCase,
    private val updateSeaCreaturesUseCase: UpdateSeaCreaturesUseCase,
) {

    suspend operator fun invoke() {
        val userIds = userRepository.getAllUsers().first()
            .map { it.id }
        userIds.forEach { id ->
            updateFishesUseCase(id)
            updateBugsUseCase(id)
            updateSeaCreaturesUseCase(id)
        }
    }

    suspend operator fun invoke(userId: Int) {
        updateFishesUseCase(userId)
        updateBugsUseCase(userId)
        updateSeaCreaturesUseCase(userId)
    }
}