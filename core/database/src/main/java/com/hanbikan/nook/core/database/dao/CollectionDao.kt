package com.hanbikan.nook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hanbikan.nook.core.database.entity.FishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Query("SELECT * FROM fish WHERE fish.user_id == :userId ORDER BY number")
    fun getAllFishesByUserId(userId: Int): Flow<List<FishEntity>>

    @Insert
    suspend fun insertFishes(vararg fishEntities: FishEntity)

    @Update
    suspend fun updateFish(fishEntity: FishEntity)

    @Query("DELETE FROM fish WHERE fish.user_id == :userId")
    fun deleteAllFishes(userId: Int)
}