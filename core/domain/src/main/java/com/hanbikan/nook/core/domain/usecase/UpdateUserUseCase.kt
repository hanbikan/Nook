package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) {
    /**
     * @param user 기존에 존재하는 [User]와 같은 [User.id]를 갖는 새로운 [User] 데이터
     */
    suspend operator fun invoke(user: User) {
        val previousUser = userRepository.getAllUsers().first().find { it.id == user.id } ?: return
        val addedUserId = userRepository.insertOrReplaceUser(user)
        val addedUser = userRepository.getUserById(addedUserId).first()

        // 반구가 변경된 경우
        if (previousUser.isNorth != addedUser?.isNorth) {
            updateUserDataUseCase()
        }
    }
}