package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    // Fish
    fun getAllFishesByUserId(userId: Int): Flow<List<Fish>>

    suspend fun insertOrReplaceFishes(fishList: List<Fish>)

    suspend fun updateFish(fish: Fish)

    // Bug
    fun getAllBugsByUserId(userId: Int): Flow<List<Bug>>

    suspend fun insertOrReplaceBugs(bugList: List<Bug>)

    suspend fun updateBug(bug: Bug)

    // Sea creature

    fun getAllSeaCreaturesByUserId(userId: Int): Flow<List<SeaCreature>>

    suspend fun insertOrReplaceSeaCreatures(seaCreatureList: List<SeaCreature>)

    suspend fun updateSeaCreature(seaCreature: SeaCreature)
}