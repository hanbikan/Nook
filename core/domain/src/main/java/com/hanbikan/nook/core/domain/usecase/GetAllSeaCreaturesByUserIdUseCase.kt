package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

private val seaCreatureNameToKorean: Map<String, String> = mapOf(
    // TODO
)

private val seaCreatureLocationToKorean: Map<String, String> = mapOf(
    // TODO
)

class GetAllSeaCreaturesByUserIdUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: Int): Flow<List<SeaCreature>> {
        return collectionRepository.getAllSeaCreaturesByUserId(userId)
            .mapLatest { seaCreatureList ->
                seaCreatureList.map {
                    it.copy(
                        name = seaCreatureNameToKorean.getOrElse(it.name) { it.name },
                        location = seaCreatureLocationToKorean.getOrElse(it.location) { it.location },
                    )
                }
            }
    }
}