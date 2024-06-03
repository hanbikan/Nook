package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>

    fun getUserById(id: Int): Flow<User?>

    suspend fun insertOrReplaceUser(user: User): Int

    suspend fun deleteUserById(id: Int)
}