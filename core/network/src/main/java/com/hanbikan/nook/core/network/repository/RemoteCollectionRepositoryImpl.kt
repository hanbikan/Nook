package com.hanbikan.nook.core.network.repository

import com.hanbikan.nook.core.domain.model.Fish
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
}