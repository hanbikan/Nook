package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateFishesUseCase @Inject constructor(
    private val getAllRemoteFishesByUserIdUseCase: GetAllRemoteFishesByUserIdUseCase,
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(userId: Int) {
        val collectedNumber = collectionRepository.getAllFishesByUserId(userId).first()
            .filter { it.isCollected }
            .map { it.number }
            .toSet()

        val remoteFishes = getAllRemoteFishesByUserIdUseCase(userId = userId).map {
            it.copy(isCollected = it.number in collectedNumber)
        }
        collectionRepository.insertOrReplaceFishes(remoteFishes)
    }
}