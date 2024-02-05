package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetActiveUserUseCase @Inject constructor(
    private val appStateRepository: AppStateRepository,
    private val userRepository: UserRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<User?> {
        return appStateRepository.getActiveUserId()
            .flatMapLatest {
                if (it == null) {
                    flowOf(null)
                } else {
                    userRepository.getUserById(it)
                }
            }
    }
}