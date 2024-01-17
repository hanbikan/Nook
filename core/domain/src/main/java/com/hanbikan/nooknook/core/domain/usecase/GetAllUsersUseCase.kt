package com.hanbikan.nooknook.core.domain.usecase

import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<List<User>> {
        return userRepository.getAllUsers()
    }
}