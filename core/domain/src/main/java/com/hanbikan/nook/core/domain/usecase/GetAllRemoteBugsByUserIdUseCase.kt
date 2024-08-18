package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.util.bugLocationToKorean
import com.hanbikan.nook.core.domain.util.bugNameToKorean
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject

class GetAllRemoteBugsByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
) {
    suspend operator fun invoke(userId: Int): List<Bug> {
        val user = userRepository.getUserById(userId).first()
        val language = appStateRepository.getLanguage().first()

        return remoteCollectionRepository.getAllBugs(
            userId = userId,
            isNorth = user?.isNorth ?: true
        ).map {
            if (language == Locale.KOREAN.language) {
                it.copy(
                    name = bugNameToKorean.getOrElse(it.name) { it.name },
                    location = bugLocationToKorean.getOrElse(it.location) { it.location },
                )
            } else {
                it
            }
        }
    }
}