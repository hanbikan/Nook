package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Fish
import javax.inject.Inject

class GetAllFishesByUserIdUseCase @Inject constructor(
    private val getFishesByUserIdUseCase: GetFishesByUserIdUseCase,
) {
    suspend operator fun invoke(userId: Int): List<Fish> {
        return getFishesByUserIdUseCase.invoke(userId)
    }
}