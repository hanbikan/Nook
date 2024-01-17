package com.hanbikan.nooknook.core.domain.usecase

import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.insertUser(user)
    }
}