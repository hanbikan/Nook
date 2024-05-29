package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Fish
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getAllFishesByUserId(userId: Int): Flow<List<Fish>>

    suspend fun insertFishes(fishList: List<Fish>)

    suspend fun updateFish(fish: Fish)

    suspend fun deleteAllFishes(userId: Int)
}