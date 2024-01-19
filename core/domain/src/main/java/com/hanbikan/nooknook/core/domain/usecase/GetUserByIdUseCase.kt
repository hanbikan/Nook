package com.hanbikan.nooknook.core.domain.usecase

import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(id: Int): Flow<User?> {
        return userRepository.getUserById(id)
    }
}