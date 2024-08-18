package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.util.fishLocationToKorean
import com.hanbikan.nook.core.domain.util.fishNameToKorean
import com.hanbikan.nook.core.domain.util.shadowSizeToKorean
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject

class GetAllRemoteFishesByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
) {
    suspend operator fun invoke(userId: Int): List<Fish> {
        val user = userRepository.getUserById(userId).first()
        val language = appStateRepository.getLanguage().first()

        return remoteCollectionRepository.getAllFishes(
            userId = userId,
            isNorth = user?.isNorth ?: true
        ).map {
            if (language == Locale.KOREAN.language) {
                it.copy(
                    name = fishNameToKorean.getOrElse(it.name) { it.name },
                    location = fishLocationToKorean.getOrElse(it.location) { it.location },
                    shadowSize = shadowSizeToKorean.getOrElse(it.shadowSize) {it.shadowSize},
                )
            } else {
                it
            }
        }
    }
}