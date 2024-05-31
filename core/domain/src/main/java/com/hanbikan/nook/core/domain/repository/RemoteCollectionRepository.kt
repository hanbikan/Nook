package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature

interface RemoteCollectionRepository {

    suspend fun getAllFishes(userId: Int, isNorth: Boolean): List<Fish>
    suspend fun getAllBugs(userId: Int, isNorth: Boolean): List<Bug>
    suspend fun getAllSeaCreatures(userId: Int, isNorth: Boolean): List<SeaCreature>
}