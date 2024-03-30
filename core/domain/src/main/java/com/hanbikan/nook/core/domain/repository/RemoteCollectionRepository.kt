package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Fish

interface RemoteCollectionRepository {

    suspend fun getAllFishes(userId: Int, isNorth: Boolean): List<Fish>
}