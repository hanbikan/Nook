package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateSeaCreaturesUseCase @Inject constructor(
    private val getAllRemoteSeaCreaturesByUserIdUseCase: GetAllRemoteSeaCreaturesByUserIdUseCase,
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(userId: Int) {
        val collectedNumber = collectionRepository.getAllSeaCreaturesByUserId(userId).first()
            .filter { it.isCollected }
            .map { it.number }
            .toSet()
        collectionRepository.deleteAllSeaCreaturesByUserId(userId)

        val remoteSeaCreatures = getAllRemoteSeaCreaturesByUserIdUseCase(userId = userId).map {
            it.copy(isCollected = it.number in collectedNumber)
        }
        collectionRepository.insertSeaCreatures(remoteSeaCreatures)
    }
}