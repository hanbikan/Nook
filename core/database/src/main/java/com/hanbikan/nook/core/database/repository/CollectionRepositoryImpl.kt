package com.hanbikan.nook.core.database.repository

import com.hanbikan.nook.core.database.dao.CollectionDao
import com.hanbikan.nook.core.database.translator.toData
import com.hanbikan.nook.core.database.translator.toDomain
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao,
) : CollectionRepository {
    // Fish
    override fun getAllFishesByUserId(userId: Int): Flow<List<Fish>> {
        return collectionDao.getAllFishesByUserId(userId).map { fishes ->
            fishes.map { it.toDomain() }
        }
    }

    override suspend fun insertOrReplaceFishes(fishList: List<Fish>) {
        collectionDao.insertOrReplaceFishes(*fishList.map { it.toData() }.toTypedArray())
    }

    override suspend fun updateFish(fish: Fish) {
        collectionDao.updateFish(fish.toData())
    }

    // Bug
    override fun getAllBugsByUserId(userId: Int): Flow<List<Bug>> {
        return collectionDao.getAllBugsByUserId(userId).map { bugs ->
            bugs.map { it.toDomain() }
        }
    }

    override suspend fun insertOrReplaceBugs(bugList: List<Bug>) {
        collectionDao.insertOrReplaceBugs(*bugList.map { it.toData() }.toTypedArray())
    }

    override suspend fun updateBug(bug: Bug) {
        collectionDao.updateBug(bug.toData())
    }

    // Sea creature
    override fun getAllSeaCreaturesByUserId(userId: Int): Flow<List<SeaCreature>> {
        return collectionDao.getAllSeaCreaturesByUserId(userId).map { seaCreatures ->
            seaCreatures.map { it.toDomain() }
        }
    }

    override suspend fun insertOrReplaceSeaCreatures(seaCreatureList: List<SeaCreature>) {
        collectionDao.insertOrReplaceSeaCreatures(*seaCreatureList.map { it.toData() }.toTypedArray())
    }

    override suspend fun updateSeaCreature(seaCreature: SeaCreature) {
        collectionDao.updateSeaCreature(seaCreature.toData())
    }
}