package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    /**
     * @param user 기존에 존재하는 [User]와 같은 [User.id]를 갖는 새로운 [User] 데이터
     */
    suspend operator fun invoke(user: User) {
        userRepository.insertOrReplaceUser(user)
    }
}