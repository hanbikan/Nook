package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import javax.inject.Inject

class UpdateFishesUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(userId: Int) {
        val remoteFishes = remoteCollectionRepository.getAllFishes(
            userId = userId,
            isNorth = true, // TODO: 북반구/남반구 정보 반영
        )
        collectionRepository.deleteAllFishes()
        collectionRepository.insertFishes(remoteFishes)
    }
}