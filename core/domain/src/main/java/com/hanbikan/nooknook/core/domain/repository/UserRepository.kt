package com.hanbikan.nooknook.core.domain.repository

import com.hanbikan.nooknook.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>

    suspend fun insertUser(user: User)

    suspend fun deleteUserById(id: Int)
}