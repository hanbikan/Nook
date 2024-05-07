package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFishesByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(userId: Int): List<Fish> {
        val localFishes = collectionRepository.getAllFishesByUserId(userId).first()
        return localFishes.ifEmpty {
            // 로컬에 없는 경우 네트워크를 호출하여 업데이트 합니다.
            val remoteFishes = remoteCollectionRepository.getAllFishes(
                userId = userId,
                isNorth = true, // TODO: 북반구/남반구 정보 반영
            )
            collectionRepository.insertFishes(remoteFishes)
            collectionRepository.getAllFishesByUserId(userId).first()
        }
    }
}