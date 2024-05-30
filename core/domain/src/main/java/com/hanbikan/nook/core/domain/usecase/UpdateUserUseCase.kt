package com.hanbikan.nook.core.domain.usecase

import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val updateFishesUseCase: UpdateFishesUseCase,
    private val updateBugsUseCase: UpdateBugsUseCase,
) {
    suspend operator fun invoke(userId: Int) {
        updateFishesUseCase(userId)
        updateBugsUseCase(userId)
    }
}