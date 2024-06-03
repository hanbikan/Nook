package com.nook.core.domain_test.repository

import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.nook.core.domain_test.data.userTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val testUserRepository = TestUserRepository()

class TestUserRepository : UserRepository {
    override fun getAllUsers(): Flow<List<User>> {
        return flowOf(userTestData)
    }

    override fun getUserById(id: Int): Flow<User?> {
        val user = userTestData.find { it.id == id }
        return flowOf(user)
    }

    override suspend fun insertOrReplaceUser(user: User): Int {
        val addedUserId = if (userTestData.isNotEmpty()) {
            userTestData.last().id + 1
        } else {
            0
        }
        val newUserTestData = userTestData.toMutableList().apply {
            add(user.copy(id = addedUserId))
        }
        userTestData = newUserTestData
        return addedUserId
    }

    override suspend fun deleteUserById(id: Int) {
        userTestData = userTestData.filter { it.id != id }
    }
}