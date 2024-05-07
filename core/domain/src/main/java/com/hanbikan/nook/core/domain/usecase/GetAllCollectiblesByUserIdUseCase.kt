package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Collectible
import javax.inject.Inject

class GetAllCollectiblesByUserIdUseCase @Inject constructor(
    getFishesByUserIdUseCase: GetFishesByUserIdUseCase,
) {
    private val useCases = listOf(
        getFishesByUserIdUseCase
    )

    suspend operator fun invoke(userId: Int): List<List<Collectible>> {
        return useCases.map { it.invoke(userId) }
    }

    suspend operator fun invoke(userId: Int, index: Int): List<Collectible> {
        return useCases[index].invoke(userId)
    }
}