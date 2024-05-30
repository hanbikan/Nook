package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

private val bugNameToKorean: Map<String, String> = mapOf(
    // TODO
)

private val bugLocationToKorean: Map<String, String> = mapOf(
    // TODO
)

class GetAllBugsByUserIdUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: Int): Flow<List<Bug>> {
        return collectionRepository.getAllBugsByUserId(userId)
            .mapLatest { bugList ->
                bugList.map {
                    it.copy(
                        name = bugNameToKorean.getOrElse(it.name) { it.name },
                        location = bugLocationToKorean.getOrElse(it.location) { it.location },
                    )
                }
            }
    }
}