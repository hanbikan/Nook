package com.hanbikan.nooknook.core.domain.usecase

import com.hanbikan.nooknook.core.domain.repository.AppStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveUserIdUseCase @Inject constructor(
    private val appStateRepository: AppStateRepository
) {
    operator fun invoke(): Flow<Int?> {
        return appStateRepository.getActiveUserId()
    }
}