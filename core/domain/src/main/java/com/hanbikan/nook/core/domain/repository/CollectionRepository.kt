package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
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

    // Sea creature

    fun getAllSeaCreaturesByUserId(userId: Int): Flow<List<SeaCreature>>

    suspend fun insertSeaCreatures(seaCreatureList: List<SeaCreature>)

    suspend fun updateSeaCreature(seaCreature: SeaCreature)

    suspend fun deleteAllSeaCreaturesByUserId(userId: Int)
}