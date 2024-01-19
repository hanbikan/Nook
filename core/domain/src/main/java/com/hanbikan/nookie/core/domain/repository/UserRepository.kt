package com.hanbikan.nookie.core.domain.repository

import com.hanbikan.nookie.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>

    fun getUserById(id: Int): Flow<User?>

    suspend fun insertUser(user: User): Int

    suspend fun deleteUserById(id: Int)
}