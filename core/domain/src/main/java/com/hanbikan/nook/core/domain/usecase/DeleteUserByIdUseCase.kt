package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        userRepository.deleteUserById(id)
    }
}