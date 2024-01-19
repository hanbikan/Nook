package com.hanbikan.nookie.core.domain.usecase

import com.hanbikan.nookie.core.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        userRepository.deleteUserById(id)
    }
}