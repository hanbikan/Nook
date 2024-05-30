package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    // Fish
    fun getAllFishesByUserId(userId: Int): Flow<List<Fish>>

    suspend fun insertFishes(fishList: List<Fish>)

    suspend fun updateFish(fish: Fish)

    suspend fun deleteAllFishesByUserId(userId: Int)

    // Bug
    fun getAllBugsByUserId(userId: Int): Flow<List<Bug>>

    suspend fun insertBugs(bugList: List<Bug>)

    suspend fun updateBug(bug: Bug)

    suspend fun deleteAllBugsByUserId(userId: Int)
}