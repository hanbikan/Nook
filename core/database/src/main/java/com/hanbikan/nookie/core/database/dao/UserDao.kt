package com.hanbikan.nookie.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hanbikan.nookie.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE user.id == :id")
    fun getUserById(id: Int): Flow<UserEntity?>

    @Insert
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("DELETE FROM user WHERE user.id == :id")
    suspend fun deleteUserById(id: Int)
}