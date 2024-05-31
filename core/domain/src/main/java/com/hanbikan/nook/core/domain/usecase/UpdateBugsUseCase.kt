package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateBugsUseCase @Inject constructor(
    private val getAllRemoteBugsByUserIdUseCase: GetAllRemoteBugsByUserIdUseCase,
    private val collectionRepository: CollectionRepository,
) {
    suspend operator fun invoke(userId: Int) {
        val collectedNumber = collectionRepository.getAllBugsByUserId(userId).first()
            .filter { it.isCollected }
            .map { it.number }
            .toSet()
        collectionRepository.deleteAllBugsByUserId(userId)

        val remoteBugs = getAllRemoteBugsByUserIdUseCase(userId = userId).map {
            it.copy(isCollected = it.number in collectedNumber)
        }
        collectionRepository.insertBugs(remoteBugs)
    }
}