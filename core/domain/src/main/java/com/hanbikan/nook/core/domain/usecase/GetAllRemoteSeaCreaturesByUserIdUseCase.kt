package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.util.seaCreatureNameToKorean
import com.hanbikan.nook.core.domain.util.shadowMovementToKorean
import com.hanbikan.nook.core.domain.util.shadowSizeToKorean
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject

class GetAllRemoteSeaCreaturesByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
) {
    suspend operator fun invoke(userId: Int): List<SeaCreature> {
        val user = userRepository.getUserById(userId).first()
        val language = appStateRepository.getLanguage().first()

        return remoteCollectionRepository.getAllSeaCreatures(
            userId = userId,
            isNorth = user?.isNorth ?: true
        ).map {
            if (language == Locale.KOREAN.language) {
                it.copy(
                    name = seaCreatureNameToKorean.getOrElse(it.name) { it.name },
                    shadowSize = shadowSizeToKorean.getOrElse(it.shadowSize) { it.shadowSize },
                    shadowMovement = shadowMovementToKorean.getOrElse(it.shadowMovement) { it.shadowMovement },
                )
            } else {
                it
            }
        }
    }
}