package com.hanbikan.nookie.core.database.repository

import com.hanbikan.nookie.core.database.dao.UserDao
import com.hanbikan.nookie.core.database.translator.toData
import com.hanbikan.nookie.core.database.translator.toDomain
import com.hanbikan.nookie.core.domain.model.User
import com.hanbikan.nookie.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {
    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { users ->
            users.map { it.toDomain() }
        }
    }

    override fun getUserById(id: Int): Flow<User?> {
        return userDao.getUserById(id).map { user ->
            user?.toDomain()
        }
    }

    override suspend fun insertUser(user: User): Int {
        return userDao.insertUser(user.toData()).toInt()
    }

    override suspend fun deleteUserById(id: Int) {
        userDao.deleteUserById(id)
    }
}