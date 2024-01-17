package com.hanbikan.nooknook.core.database.repository

import com.hanbikan.nooknook.core.database.dao.UserDao
import com.hanbikan.nooknook.core.database.translator.toData
import com.hanbikan.nooknook.core.database.translator.toDomain
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.repository.UserRepository
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

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toData())
    }

    override suspend fun deleteUserById(id: Int) {
        userDao.deleteUserById(id)
    }
}