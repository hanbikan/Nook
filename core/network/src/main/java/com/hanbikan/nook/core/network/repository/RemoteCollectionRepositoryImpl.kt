package com.hanbikan.nook.core.network.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.network.service.NookipediaService
import com.hanbikan.nook.core.network.translator.toDomain
import javax.inject.Inject

class RemoteCollectionRepositoryImpl @Inject constructor(
    private val nookipediaService: NookipediaService,
) : RemoteCollectionRepository {
    override suspend fun getAllFishes(userId: Int, isNorth: Boolean): List<Fish> {
        return nookipediaService.getAllFishes().map {
            it.toDomain(userId, isNorth)
        }
    }

    override suspend fun getAllBugs(userId: Int, isNorth: Boolean): List<Bug> {
        return nookipediaService.getAllBugs().map {
            it.toDomain(userId, isNorth)
        }
    }

    override suspend fun getAllSeaCreatures(userId: Int, isNorth: Boolean): List<SeaCreature> {
        return nookipediaService.getAllSeaCreatures().map {
            it.toDomain(userId, isNorth)
        }
    }
}