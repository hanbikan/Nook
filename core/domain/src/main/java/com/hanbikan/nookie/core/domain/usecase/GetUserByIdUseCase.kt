package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.model.User
import com.hanbikan.nookie.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(id: Int?): Flow<User?> {
        if (id == null) return flowOf(null)
        return userRepository.getUserById(id)
    }
}