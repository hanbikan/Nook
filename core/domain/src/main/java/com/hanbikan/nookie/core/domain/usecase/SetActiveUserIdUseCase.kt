package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.repository.AppStateRepository
import javax.inject.Inject

class SetActiveUserIdUseCase @Inject constructor(
    private val appStateRepository: AppStateRepository
) {
    suspend operator fun invoke(id: Int) {
        appStateRepository.setActiveUserId(id)
    }
}