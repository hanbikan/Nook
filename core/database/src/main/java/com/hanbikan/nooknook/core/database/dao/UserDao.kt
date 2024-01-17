package com.hanbikan.nooknook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hanbikan.nooknook.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user WHERE user.id == :id")
    suspend fun deleteUserById(id: Int)
}