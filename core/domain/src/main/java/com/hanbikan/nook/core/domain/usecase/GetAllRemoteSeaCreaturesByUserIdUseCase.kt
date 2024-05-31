package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import javax.inject.Inject

private val seaCreatureNameToKorean: Map<String, String> = mapOf(
    // TODO
)

class GetAllRemoteSeaCreaturesByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
) {
    suspend operator fun invoke(userId: Int): List<SeaCreature> {
        return remoteCollectionRepository.getAllSeaCreatures(
            userId = userId,
            isNorth = true // TODO
        ).map {
            it.copy(
                name = seaCreatureNameToKorean.getOrElse(it.name) { it.name },
            )
        }
    }
}