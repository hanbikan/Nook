package com.hanbikan.nook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    // Fish
    @Query("SELECT * FROM fish WHERE fish.user_id == :userId ORDER BY number")
    fun getAllFishesByUserId(userId: Int): Flow<List<FishEntity>>

    @Insert
    suspend fun insertFishes(vararg fishEntities: FishEntity)

    @Update
    suspend fun updateFish(fishEntity: FishEntity)

    @Query("DELETE FROM fish WHERE fish.user_id == :userId")
    fun deleteAllFishes(userId: Int)

    // Bug
    @Query("SELECT * FROM bug WHERE bug.user_id == :userId ORDER BY number")
    fun getAllBugsByUserId(userId: Int): Flow<List<BugEntity>>

    @Insert
    suspend fun insertBugs(vararg bugEntities: BugEntity)

    @Update
    suspend fun updateBug(bugEntity: BugEntity)

    @Query("DELETE FROM bug WHERE bug.user_id == :userId")
    fun deleteAllBugs(userId: Int)
}